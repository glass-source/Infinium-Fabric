package com.infinium.global.utils;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Animation {
    private static ScheduledFuture<?> task;


    public static void initImageForAll() {
        var instance = Infinium.getInstance();
        var core = instance.getCore();
        var executor = instance.getExecutor();
        var server = core.getServer();
        var audience = core.getAdventure().audience(PlayerLookup.all(server));
        var animationList = getFramesCharsIntegers(0, 79);
        final int[] i = {0};
        task = executor.scheduleWithFixedDelay(() -> {
            sendTitle(audience, animationList.get(i[0]), 0, 20, 20);
            i[0]++;
            if(i[0] >= animationList.size()) {
                task.cancel(true);
                task = null;
            }
        }, 0, 80, TimeUnit.MILLISECONDS);

    }

    public static void sendTitle(Audience audience, char character, int fadeIn, int stay, int fadeOut) {
        audience.showTitle(Title.title(Component.text(Character.toString(character)), Component.text(""), Title.Times.times(Duration.ofSeconds(fadeIn/20), Duration.ofSeconds(fadeOut/20), Duration.ofSeconds(fadeOut/20))));
    }

    public static List<Character> getFramesCharsIntegers(int from, int until) {
        List<Character> chars = new ArrayList<>();

        while (from <= until)
            chars.add(unescapeString(String.format("\\uE%03d", from++)).charAt(0));

        return chars;
    }

    private static String unescapeString(String oldstr) {

        /*
         * In contrast to fixing Java's broken regex charclasses, this one need be no
         * bigger, as unescaping shrinks the string here, where in the other one, it
         * grows it.
         */

        StringBuilder newString = new StringBuilder(oldstr.length());

        boolean saw_backslash = false;

        for (int i = 0; i < oldstr.length(); i++) {
            int cp = oldstr.codePointAt(i);
            if (oldstr.codePointAt(i) > Character.MAX_VALUE) {
                i++; /**** WE HATES UTF-16! WE HATES IT FOREVERSES!!! ****/
            }

            if (!saw_backslash) {
                if (cp == '\\') {
                    saw_backslash = true;
                } else {
                    newString.append(Character.toChars(cp));
                }
                continue; /* switch  \*/
            }

            if (cp == '\\') {
                saw_backslash = false;
                newString.append('\\');
                newString.append('\\');
                continue; /* switch */
            }

            switch (cp) {

                case 'r':
                    newString.append('\r');
                    break; /* switch */

                case 'n':
                    newString.append('\n');
                    break; /* switch */

                case 'f':
                    newString.append('\f');
                    break; /* switch */

                /* PASS a \b THROUGH!! */
                case 'b':
                    newString.append("\\b");
                    break; /* switch */

                case 't':
                    newString.append('\t');
                    break; /* switch */

                case 'a':
                    newString.append('\007');
                    break; /* switch */

                case 'e':
                    newString.append('\033');
                    break; /* switch */

                /*
                 * A "control" character is what you getStatLevelPercentaje when you xor its codepoint with
                 * '@'==64. This only makes sense for ASCII, and may not yield a "control"
                 * character after all.
                 *
                 * Strange but true: "\c{" is ";", "\c}" is "=", etc.
                 */
                case 'c': {
                    if (++i == oldstr.length()) {
                        die("trailing \\c");
                    }
                    cp = oldstr.codePointAt(i);
                    /*
                     * don't need to grok surrogates, as next line blows them up
                     */
                    if (cp > 0x7f) {
                        die("expected ASCII after \\c");
                    }
                    newString.append(Character.toChars(cp ^ 64));
                    break; /* switch */
                }

                case '8':
                case '9':
                    die("illegal octal digit");
                    /* NOTREACHED */

                    /*
                     * may be 0 to 2 octal digits following this one so back up one for fallthrough
                     * to next case; unread this digit and fall through to next case.
                     */
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    --i;
                    /* FALLTHROUGH */

                    /*
                     * Can have 0, 1, or 2 octal digits following a 0 this permits larger values
                     * than octal 377, up to octal 777.
                     */
                case '0': {
                    if (i + 1 == oldstr.length()) {
                        /* found \0 at end of string */
                        newString.append(Character.toChars(0));
                        break; /* switch */
                    }
                    i++;
                    int digits = 0;
                    int j;
                    for (j = 0; j <= 2; j++) {
                        if (i + j == oldstr.length()) {
                            break; /* for */
                        }
                        /* safe because will unread surrogate */
                        int ch = oldstr.charAt(i + j);
                        if (ch < '0' || ch > '7') {
                            break; /* for */
                        }
                        digits++;
                    }
                    if (digits == 0) {
                        --i;
                        newString.append('\0');
                        break; /* switch */
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + digits), 8);
                    } catch (NumberFormatException nfe) {
                        die("invalid octal value for \\0 escape");
                    }
                    newString.append(Character.toChars(value));
                    i += digits - 1;
                    break; /* switch */
                } /* end case '0' */

                case 'x': {
                    if (i + 2 > oldstr.length()) {
                        die("string too short for \\x escape");
                    }
                    i++;
                    boolean saw_brace = false;
                    if (oldstr.charAt(i) == '{') {
                        /* ^^^^^^ ok to ignore surrogates here */
                        i++;
                        saw_brace = true;
                    }
                    int j;
                    for (j = 0; j < 8; j++) {

                        if (!saw_brace && j == 2) {
                            break; /* for */
                        }

                        /*
                         * ASCII test also catches surrogates
                         */
                        int ch = oldstr.charAt(i + j);
                        if (ch > 127) {
                            die("illegal non-ASCII hex digit in \\x escape");
                        }

                        if (saw_brace && ch == '}') {
                            break;
                            /* for */ }

                        if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F'))) {
                            die(String.format("illegal hex digit #%d '%c' in \\x", ch, ch));
                        }

                    }
                    if (j == 0) {
                        die("empty braces in \\x{} escape");
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\x escape");
                    }
                    newString.append(Character.toChars(value));
                    if (saw_brace) {
                        j++;
                    }
                    i += j - 1;
                    break; /* switch */
                }

                case 'u': {
                    if (i + 4 > oldstr.length()) {
                        die("string too short for \\u escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 4; j++) {
                        /* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            die("illegal non-ASCII hex digit in \\u escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\u escape");
                    }
                    newString.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                case 'U': {
                    if (i + 8 > oldstr.length()) {
                        die("string too short for \\U escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 8; j++) {
                        /* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            die("illegal non-ASCII hex digit in \\U escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        die("invalid hex value for \\U escape");
                    }
                    newString.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                default:
                    newString.append('\\');
                    newString.append(Character.toChars(cp));
                    /*
                     * say(String.format( "DEFAULT unrecognized escape %c passed through", cp));
                     */
                    break; /* switch */

            }
            saw_backslash = false;
        }

        /* weird to leave one at the end */
        if (saw_backslash) {
            newString.append('\\');
        }

        return newString.toString();
    }

    /**
     * Private util to crash with a useful message.
     *
     * @param foa String to be printed in the exception
     */
    private static final void die(String foa) {
        throw new IllegalArgumentException(foa);
    }

}
