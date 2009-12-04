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
package ar.com.zauber.labs.kraken.providers.wikipedia.apps.administrative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.LangUtils;

/**
 * Generates a TSV file to be loaded using 
 * http://wiki.freebase.com/wiki/Freebase_Loader
 * to load argentina adminstrative divitions.
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public final class MainAdministrative {

    /** utility class */
    private MainAdministrative() {
        // void
    }
    
    /**
     * Lee la pagina 
     * http://es.wikipedia.org/wiki/Departamentos_y_partidos_de_la_Argentina
     * la tabla de localidades
     */
    public static void main(final String[] args) 
        throws IOException, JAXBException, SAXException, URISyntaxException {
        final ConfigurableApplicationContext ctx = 
            new ClassPathXmlApplicationContext(
                "ar/com/zauber/labs/kraken/providers/wikipedia/apps/administrative/"
                + "config/context-administrative-kraken-spring.xml");
        ctx.registerShutdownHook();
        
        new ArgentinaDepartmentsSync(new BufferedReader(new InputStreamReader(
            ArgentinaDepartmentsSync.class.getResourceAsStream("es.txt"),
                "utf-8")), (WikiPageRetriever)ctx.getBean("httpWikiPageRetriever"),
                LangUtils.ES, 
                (Closure)ctx.getBean("administrativeClosure"));
    }
}
