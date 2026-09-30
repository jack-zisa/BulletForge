package dev.creoii.forge.parse;

import dev.creoii.forge.ForgeParseException;
import dev.creoii.forge.FunctionRegistry;
import dev.creoii.forge.script.*;
import dev.creoii.forge.value.datatype.DataType;
import dev.creoii.forge.value.datatype.DataTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ForgeParser {
    public static Forgescript parse(String s) {
        List<String> lines = Arrays.stream(s.split("\n")).filter(s1 -> !s1.isBlank()).map(String::trim).toList();

        int version = parseVersion(lines.getFirst());
        if (version == -1) throw ForgeParseException.createVersionException(String.valueOf(version));

        Expression[] expressions = new Expression[lines.size() - 1];
        for (int i = 1; i < lines.size(); ++i) {
            expressions[i - 1] = parseExpression(lines.get(i));
        }

        return new Forgescript(version, expressions);
    }

    public static int parseVersion(String s) {
        int versionOffset = "version".length() + 1;
        String version = s.substring(versionOffset);
        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Expression parseExpression(String s) {
        int startI = s.indexOf('(');

        String functionId = s.substring(0, startI);
        String s1 = s.substring(startI + 1, s.length() - 1);

        Expression expression = new Expression(s, FunctionRegistry.get(functionId));

        List<String> tokens = parseTokens(s1);

        int index = 0;
        while (index < tokens.size()) {
            ParseResult result = parseNode(tokens, index, expression.context(), DataTypes.VOID);
            expression.node().add(result.node());
            index = result.nextIndex();
        }

        return expression;
    }

    public static ParseResult parseNode(List<String> tokens, int index, ForgeContext context, DataType<?> dataType) {
        String token = tokens.get(index);

        if (FunctionRegistry.isKeyword(token)) {
            FunctionDefinition definition = FunctionRegistry.get(token);
            FunctionNode node = new FunctionNode(definition);

            int current = index + 1;

            for (int i = 0; i < definition.args().length; ++i) {
                DataType<?> expectedType = definition.args()[i];

                ParseResult result = parseNode(tokens, current, context, expectedType);

                node.add(result.node());
                current = result.nextIndex();
            }
            return new ParseResult(node, current);
        }

        if (token.startsWith("\"") && token.endsWith("\"")) {
            return new ParseResult(new ConstantNode<>(DataTypes.STRING, token.substring(1, token.length() - 1)), index + 1);
        }

        if (isNumber(token)) {
            return new ParseResult(new ConstantNode<>(DataTypes.NUMBER, Double.parseDouble(token)), index + 1);
        }

        context.add(token);

        return new ParseResult(new VariableNode<>(dataType, token), index + 1);
    }

    public static List<String> parseTokens(String representation) {
        final List<String> tokens = new ArrayList<>();
        char[] chars = representation.toCharArray();

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];

            if (c == '"') {
                int start = i++;

                while (i < chars.length && chars[i] != '"') {
                    if (chars[i] == '\\' && i + 1 < chars.length) {
                        ++i;
                    }
                    ++i;
                }

                if (i >= chars.length) {
                    throw new IllegalArgumentException("Unclosed string");
                }

                ++i;
                tokens.add(representation.substring(start, i));
                continue;
            }

            if (Character.isWhitespace(c) || c == '(' || c == ')' || c == ',') {
                continue;
            }

            int start = i;

            while (i < chars.length) {
                c = chars[i];

                if (Character.isWhitespace(c) || c == '(' || c == ')' || c == ',') {
                    break;
                }

                ++i;
            }

            tokens.add(representation.substring(start, i));
            --i;
        }

        return tokens;
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public record ParseResult(Node node, int nextIndex) {}
}
