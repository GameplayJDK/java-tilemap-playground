/*
 * The MIT License (MIT)
 * Copyright (c) 2019 GameplayJDK
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.gameplayjdk.jwfcimage.utility;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemanticVersion {

    /**
     * one with numbered capture groups instead (so cg1 = major, cg2 = minor, cg3 = patch, cg4 = prerelease and cg5 = buildmetadata)
     * that is compatible with ECMA Script (JavaScript), PCRE (Perl Compatible Regular Expressions, i.e. Perl, PHP and R), Python and Go.
     */
    private static final String REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$";

    public static SemanticVersion create(String versionString) {
        // Support the "vX.Y.Z" prefix syntax.
        if ('v' == versionString.charAt(0)) {
            versionString = versionString.substring(1);
        }

        SemanticVersion semanticVersion = new SemanticVersion(versionString);
        if (semanticVersion.parse()) {
            return semanticVersion;
        }

        return null;
    }

    private final String source;

    private int majorVersion;
    private int minorVersion;
    private int patchVersion;

    private String preRelease;

    private String buildMetaData;

    private SemanticVersion(String source) {
        this.source = source;
    }

    public boolean parse() {
        Pattern pattern = Pattern.compile(SemanticVersion.REGEX);
        Matcher matcher = pattern.matcher(this.source);

        try {
            this.majorVersion = Integer.parseInt(matcher.group(1));
            this.minorVersion = Integer.parseInt(matcher.group(2));
            this.patchVersion = Integer.parseInt(matcher.group(3));

            this.preRelease = matcher.group(4);

            this.buildMetaData = matcher.group(5);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();

            return false;
        }

        return true;
    }

    // TODO: Fix comparison.

    public int compare(SemanticVersion semanticVersion) {
        if (this.majorVersion != semanticVersion.getMajorVersion()) {
            return Integer.signum(this.majorVersion - semanticVersion.getMajorVersion());
        }

        if (this.minorVersion != semanticVersion.getMinorVersion()) {
            return Integer.signum(this.minorVersion - semanticVersion.getMajorVersion());
        }

        if (this.patchVersion != semanticVersion.getPatchVersion()) {
            return Integer.signum(this.patchVersion - semanticVersion.getPatchVersion());
        }

        return 0;
    }

    public int compare(SemanticVersion semanticVersion, Comparator<SemanticVersion> comparator) {
        int compare = this.compare(semanticVersion);

        if (0 == compare) {
            return comparator.compare(this, semanticVersion);
        }

        return compare;
    }

    public String getSource() {
        return this.source;
    }

    public Integer getMajorVersion() {
        return this.majorVersion;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public int getPatchVersion() {
        return this.patchVersion;
    }

    public String getPreRelease() {
        return this.preRelease;
    }

    public String getBuildMetaData() {
        return this.buildMetaData;
    }
}
