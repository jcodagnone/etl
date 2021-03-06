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
package ar.com.zauber.labs.kraken.providers.wikipedia.model;

/**
 * Language
 * 
 * @author Juan F. Codagnone
 * @since Sep 13, 2009
 */
public interface Language extends Comparable<Language> {

    /** @see http://www.ietf.org/rfc/rfc4646.txt */
    String getIsoName();
}
