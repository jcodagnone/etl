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
package ar.com.zauber.labs.kraken.providers.wikipedia.schema;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import ar.com.zauber.labs.kraken.schema.model.SchemaProvider;
import ar.com.zauber.labs.kraken.schema.model.impl.AbstractSchemaProvider;

/**
 * {@link SchemaProvider} for Wiki Bot API
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class WikipediaSchemaProvider extends AbstractSchemaProvider {
    /** Creates the WikipediaSchemaProvider. */ 
    public WikipediaSchemaProvider() throws SAXException, JAXBException {
        super("ar/com/zauber/labs/kraken/providers/wikiedia/schema/"
            + "wikibotapi-links-langlinks.xsd",
            "ar.com.zauber.labs.semantic.administrative.wikipedia");
    }
}
