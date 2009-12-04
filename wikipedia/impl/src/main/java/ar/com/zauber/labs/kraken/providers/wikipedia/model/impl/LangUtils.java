/**
 * Copyright (c) 2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.labs.kraken.providers.wikipedia.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;

/**
 * Language utilities
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 13, 2009
 */
public final class LangUtils {
    /** lenguajes ala rfc rfc4646 */
    private static final Map<String, Language> KNOWN_LANGUAGES;
    
    /** spanish */
    public static final Language ES;
    
    /** english */
    public static final Language EN;
    
    static {
        final Map<String, Language> langs = new HashMap<String, Language>();
        for(final String lang : Arrays.asList("bat-smg", "lmo", "simple", 
                "vec", "war", "zh-min-nan", "pms", "lij", "scn", "tet", "szl",
                "nap", "diq")) {
            langs.put(lang, new InmutableLanguage(lang));
        }
        for(final String s : Locale.getISOLanguages()) {
            langs.put(s, new InmutableLanguage(s));
        }
        KNOWN_LANGUAGES = Collections.unmodifiableMap(langs);
        langs.put("bat-smg", new InmutableLanguage("bat-smg"));
        ES = getLang("es");
        EN = getLang("en");
        
    }
    
    /** utility class */
    private LangUtils() {
        // void
    }
    
    /** tira un {@link IllegalArgumentException} si el lenguaje no es conocido */
    public static void validateLang(final String lang) {
        if(KNOWN_LANGUAGES != null) { // puede estar inicializandose 
            Validate.isTrue(KNOWN_LANGUAGES.containsKey(lang),
                    lang + " no es un lenguaje rfc4646 valido");
        }
    }
    
    /** retorna un lenguage (y tira {@link NoSuchEntityException} si no existe */
    public static Language getLang(final String lang) {
        final Language ret = KNOWN_LANGUAGES.get(lang);
        if(ret == null) {
            throw new NoSuchEntityException(lang);
        }
        return ret;
    }
}
