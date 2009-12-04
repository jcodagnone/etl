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

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;


/**
 * Not implented wiki page retriever (usefull for tests)
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class NotImplementedWikiPageRetriever implements WikiPageRetriever {

    /** @see WikiPageRetriever#get(Language, String) */
    public final WikiPage get(final Language language, final String pageTitle) {
        throw new NotImplementedException("not implemented");
    }

}
