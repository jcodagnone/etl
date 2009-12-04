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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.lang.Validate;
import org.junit.Assert;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;

/**
 * Lazy loading {@link WikiPageRetriever}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class LazyWikiPageRetriever implements WikiPageRetriever {
    private final WikiPageRetriever wikiPageRetriever;
    
    /** @param wikiPageRetriever used to retrieve the wiki pages */
    public LazyWikiPageRetriever(final WikiPageRetriever wikiPageRetriever) {
        this.wikiPageRetriever = wikiPageRetriever;
    }
    
    /** @see WikiPageRetriever#get(Language, String) */
    public final WikiPage get(final Language language, final String pageTitle) {
        return (WikiPage) Proxy.newProxyInstance(
                getClass().getClassLoader(), 
                new Class<?>[]{WikiPage.class}, 
                new LazyWikiPageInvocationHandler(language, pageTitle, 
                        wikiPageRetriever));
    }
}

/**
 * Implementación dinamica de {@link WikiPage} para simular un Lazy Loading 
 * 
 * @author Juan F. Codagnone
 * @since Sep 13, 2009
 */
class LazyWikiPageInvocationHandler implements InvocationHandler {
    private final Language lang;
    private final String title;
    private final WikiPageRetriever wikiPageRetriever;
    
    private WikiPage  target;

    /**
     * Creates the LazyWikiPageInvocationHandler.
     *
     */
    public LazyWikiPageInvocationHandler(final Language lang, final String title,
            final WikiPageRetriever wikiPageRetriever) {
        Validate.notNull(lang);
        Validate.notNull(title);
        Validate.notNull(wikiPageRetriever);
        
        this.lang = lang;
        this.title = title;
        this.wikiPageRetriever = wikiPageRetriever;
    }
    
    
    /** @see InvocationHandler#invoke(Object, Method, Object[]) */
    public Object invoke(final Object proxy, final Method method,
            final Object[] args) throws Throwable {
        final Object ret;

        if ("getLanguage".equals(method.getName()) && args == null) {
            ret = lang;
        } else if("toString".equals(method.getName()) && args == null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("{pageid: ");
            sb.append("TBL!");
            sb.append(", lang: ");
            sb.append(lang);
            sb.append(", titles: ");
            sb.append("TBL!");
            sb.append(", langtitles: [");
            sb.append(title);
            sb.append("]}");
            ret = sb.toString();
        } else {
            target = wikiPageRetriever.get(lang, title);
            Assert.assertNotNull("target wiki page retriever returned null page",
                    target);
            ret = method.invoke(target, args);
        }
        return ret;
    }
}
