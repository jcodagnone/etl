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
import java.util.Formatter;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.InmutableWikiLink;

/**
 * Scrape data out of Deparments of Argentina Wiki
 * 
 * @author Juan F. Codagnone
 * @since Sep 8, 2009Screen
 */
public class ArgentinaDepartmentsSync {
    private final Logger logger = Logger.getLogger(ArgentinaDepartmentsSync.class);
    private int lineNumber = 1;
    private final WikiPageRetriever wikiPageRetriever;
    private final Language lang;
    private final Closure<ArgentinaWikiSecondLevel> closure;
    
    private String line;
    
    /** Creates the ArgentinaDepartmentsSync. */
    public ArgentinaDepartmentsSync(final BufferedReader reader, 
            final WikiPageRetriever wikiPageRetriever,
            final Language lang, 
            final Closure<ArgentinaWikiSecondLevel> closure) throws IOException {
        Validate.notNull(reader);
        Validate.notNull(wikiPageRetriever);
        Validate.notNull(lang);
        Validate.notNull(closure);
        
        this.wikiPageRetriever = wikiPageRetriever;
        this.lang = lang;
        this.closure = closure;
        
        String l = null;
        while((l = reader.readLine()) != null) {
            line = l;
            parseTableLine(l);
            lineNumber++;
        }
    }
    
    /**
     * Entrada de la tabla. 3 columnas: departamento, cabecera, partido.
     * 
     * [[Departamento Alberdi]] / [[Campo Gallo]] / 
     * [[Provincia de Santiago del Estero|Santiago del Estero]]
     */
    public final void parseTableLine(final String l) {
        Validate.notNull(l);
        if(l.trim().length() != 0) {
            final String []rows = l.split("/");
            if(rows.length == 3) {
                try {
                    parseTableLine(nullify(rows[0].trim()), 
                            nullify(rows[1].trim()),
                            nullify(rows[2].trim()));
                } catch(final Throwable t) {
                    logger.error("Procesing line "
                            + lineNumber + ": "
                            + t.getLocalizedMessage(), t);
                }
            } else {
                final StringBuilder sb = new StringBuilder();
                new Formatter(sb).format(
                   "Procesing line %d: Expected 3 columns. Got %d. `%s'",
                   lineNumber, rows.length, l);
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }
    

    /** procesa las columnas de las tablas */
    public final void parseTableLine(final String linkDepartamento,
            final String linkCabecera, final  String linkProvincia) {
        if(linkDepartamento == null || linkCabecera == null 
                || linkProvincia == null) {
            logger.warn("ignoring line " + lineNumber + ": tiene parametros nulos:"
                    + line);
        } else {
            final WikiLink departamentoWikiLink = 
                new InmutableWikiLink(linkDepartamento, wikiPageRetriever, lang);
            final WikiLink cabeceraWikiLink = new InmutableWikiLink(linkCabecera,
                    wikiPageRetriever, lang);
            final WikiLink provinciaWikiLink = new InmutableWikiLink(linkProvincia,
                    wikiPageRetriever, lang);
            
            closure.execute(new ArgentinaWikiSecondLevel(provinciaWikiLink, 
                    departamentoWikiLink, cabeceraWikiLink));
        }
    }

    /** 
     * convierte represantaciones textuales de null en un null real.
     * Ej -
     */
    public final String nullify(final String s) {
        return s.equals("-") ? null : s;
    }
}

