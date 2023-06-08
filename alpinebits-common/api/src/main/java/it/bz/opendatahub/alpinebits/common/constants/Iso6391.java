// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * This enum contains all ISO 639-1 language codes.
 */
public enum Iso6391 {

    ABKHAZIAN("ab"),
    AFAR("aa"),
    AFRIKAANS("af"),
    AKAN("ak"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARAGONESE("an"),
    ARMENIAN("hy"),
    ASSAMESE("as"),
    AVARIC("av"),
    AVESTAN("ae"),
    AYMARA("ay"),
    AZERBAIJANI("az"),
    BAMBARA("bm"),
    BASHKIR("ba"),
    BASQUE("eu"),
    BELARUSIAN("be"),
    BENGALI("bn"),
    BIHARI_LANGUAGES("bh"),
    BISLAMA("bi"),
    BOSNIAN("bs"),
    BRETON("br"),
    BULGARIAN("bg"),
    BURMESE("my"),
    CATALAN("ca"),
    CHAMORRO("ch"),
    CHECHEN("ce"),
    CHICHEWA("ny"),
    CHINESE("zh"),
    CHUVASH("cv"),
    CORNISH("kw"),
    CORSICAN("co"),
    CREE("cr"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DIVEHI("dv"),
    DUTCH("nl"),
    DZONGKHA("dz"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    EWE("ee"),
    FAROESE("fo"),
    FIJIAN("fj"),
    FINNISH("fi"),
    FRENCH("fr"),
    FULAH("ff"),
    GALICIAN("gl"),
    GEORGIAN("ka"),
    GERMAN("de"),
    GREEK("el"),
    GUARANI("gn"),
    GUJARATI("gu"),
    HAITIAN("ht"),
    HAUSA("ha"),
    HEBREW("he"),
    HERERO("hz"),
    HINDI("hi"),
    HIRI_MOTU("ho"),
    HUNGARIAN("hu"),
    INTERLINGUA("ia"),
    INDONESIAN("id"),
    INTERLINGUE("ie"),
    IRISH("ga"),
    IGBO("ig"),
    INUPIAQ("ik"),
    IDO("io"),
    ICELANDIC("is"),
    ITALIAN("it"),
    INUKTITUT("iu"),
    JAPANESE("ja"),
    JAVANESE("jv"),
    KALAALLISUT("kl"),
    KANNADA("kn"),
    KANURI("kr"),
    KASHMIRI("ks"),
    KAZAKH("kk"),
    CENTRAL_KHMER("km"),
    KIKUYU("ki"),
    KINYARWANDA("rw"),
    KIRGHIZ("ky"),
    KOMI("kv"),
    KONGO("kg"),
    KOREAN("ko"),
    KURDISH("ku"),
    KUANYAMA("kj"),
    LATIN("la"),
    LUXEMBOURGISH("lb"),
    GANDA("lg"),
    LIMBURGAN("li"),
    LINGALA("ln"),
    LAO("lo"),
    LITHUANIAN("lt"),
    LUBA_KATANGA("lu"),
    LATVIAN("lv"),
    MANX("gv"),
    MACEDONIAN("mk"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MALTESE("mt"),
    MAORI("mi"),
    MARATHI("mr"),
    MARSHALLESE("mh"),
    MONGOLIAN("mn"),
    NAURU("na"),
    NAVAJO("nv"),
    NORTH_NDEBELE("nd"),
    NEPALI("ne"),
    NDONGA("ng"),
    NORWEGIAN_BOKMAL("nb"),
    NORWEGIAN_NYNORSK("nn"),
    NORWEGIAN("no"),
    SICHUAN_YI("ii"),
    SOUTH_NDEBELE("nr"),
    OCCITAN("oc"),
    OJIBWA("oj"),
    CHURCH_SLAVIC("cu"),
    OROMO("om"),
    ORIYA("or"),
    OSSETIAN("os"),
    PUNJABI("pa"),
    PALI("pi"),
    PERSIAN("fa"),
    POLISH("pl"),
    PASHTO("ps"),
    PORTUGUESE("pt"),
    QUECHUA("qu"),
    ROMANSH("rm"),
    RUNDI("rn"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SANSKRIT("sa"),
    SARDINIAN("sc"),
    SINDHI("sd"),
    NORTHERN_SAMI("se"),
    SAMOAN("sm"),
    SANGO("sg"),
    SERBIAN("sr"),
    GAELIC("gd"),
    SHONA("sn"),
    SINHALA("si"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SOMALI("so"),
    SOUTHERN_SOTHO("st"),
    SPANISH("es"),
    SUNDANESE("su"),
    SWAHILI("sw"),
    SWATI("ss"),
    SWEDISH("sv"),
    TAMIL("ta"),
    TELUGU("te"),
    TAJIK("tg"),
    THAI("th"),
    TIGRINYA("ti"),
    TIBETAN("bo"),
    TURKMEN("tk"),
    TAGALOG("tl"),
    TSWANA("tn"),
    TONGA("to"),
    TURKISH("tr"),
    TSONGA("ts"),
    TATAR("tt"),
    TWI("tw"),
    TAHITIAN("ty"),
    UIGHUR("ug"),
    UKRAINIAN("uk"),
    URDU("ur"),
    UZBEK("uz"),
    VENDA("ve"),
    VIETNAMESE("vi"),
    VOLAPUK("vo"),
    WALLOON("wa"),
    WELSH("cy"),
    WOLOF("wo"),
    WESTERN_FRISIAN("fy"),
    XHOSA("xh"),
    YIDDISH("yi"),
    YORUBA("yo"),
    ZHUANG("za"),
    ZULU("zu");

    private final String code;

    Iso6391(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isCodeDefined(String code) {
        if (code == null) {
            return false;
        }

        for (Iso6391 value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

}
