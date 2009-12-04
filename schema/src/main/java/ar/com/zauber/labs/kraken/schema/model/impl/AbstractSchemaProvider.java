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
package ar.com.zauber.labs.kraken.schema.model.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import ar.com.zauber.labs.kraken.schema.model.SchemaProvider;

/**
 * Base class for {@link SchemaProvider}s
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public abstract class AbstractSchemaProvider implements SchemaProvider {
    private final Unmarshaller unmarshaller;
    
    /** Creates the AbstractSchemaProvider.   */
    public AbstractSchemaProvider(final String xsdClasspath,
            final String packagePath) throws SAXException, JAXBException {
        final SchemaFactory sf = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        final Schema schema = sf.newSchema(AbstractSchemaProvider.class
                .getClassLoader().getResource(xsdClasspath));
        final JAXBContext jaxbContext = JAXBContext.newInstance(packagePath);
        unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
    }

    /** @see SchemaProvider#getUnmarshaller() */
    public final Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }
}
