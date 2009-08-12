package org.coode.www.servlet;

import org.coode.html.OWLHTMLKit;
import org.coode.html.doclet.HTMLDoclet;
import org.coode.html.impl.OWLHTMLConstants;
import org.coode.html.impl.OWLHTMLProperty;
import org.coode.html.impl.OWLHTMLParam;
import org.coode.html.page.EmptyOWLDocPage;
import org.coode.owl.mngr.*;
import org.coode.www.doclet.OptionsTableDoclet;
import org.coode.www.exception.OntServerException;
import org.coode.www.exception.RedirectException;
import org.coode.www.mngr.SessionManager;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
/*
* Copyright (C) 2007, University of Manchester
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * Author: Nick Drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Aug 7, 2007<br><br>
 */
public class ServerOptions extends AbstractOntologyServerServlet {

    protected void handleXMLRequest(Map<OWLHTMLParam, String> params, OWLHTMLKit kit, URL servletURL, PrintWriter out) throws OntServerException {
        boolean success = handleOptionsSet(params, kit);

//        if (success){
//            out.println("<options><" + option + " value='" + value + "'/></options>");
//        }
    }

    protected HTMLDoclet handleHTMLRequest(Map<OWLHTMLParam, String> params, OWLHTMLKit kit, URL pageURL) throws OntServerException {
        boolean success = handleOptionsSet(params, kit);

        if (success){
            throw new RedirectException(kit.getURLScheme().getURLForRelativePage(OWLHTMLConstants.OPTIONS_HTML));
        }
        else{
            EmptyOWLDocPage page = new EmptyOWLDocPage(kit);
            page.addDoclet(new OptionsTableDoclet(params, kit));
            return page;
        }
    }

    private boolean handleOptionsSet(Map<OWLHTMLParam, String> params, OWLHTMLKit kit) throws OntServerException {
        boolean success = false;

        String propertyName = params.get(OWLHTMLParam.property);
        if (propertyName != null){
            String value = params.get(OWLHTMLParam.value);

            try{
                OWLHTMLProperty property = OWLHTMLProperty.valueOf(propertyName);
                ServerPropertiesAdapter<OWLHTMLProperty> serverProperties = kit.getHTMLProperties();
                switch(property){
                    case optionUseFrames:
                        if (value.equals(OWLHTMLConstants.NO_FRAMES) || value.equals(Boolean.FALSE.toString())){
                            if (serverProperties.get(OWLHTMLProperty.optionContentWindow) != null){
                                serverProperties.set(OWLHTMLProperty.optionContentWindow, null);
                                success = true;
                            }
                        }
                        else if (value.equals(OWLHTMLConstants.SHOW_FRAMES) || value.equals(Boolean.TRUE.toString())){
                            if (serverProperties.get(OWLHTMLProperty.optionContentWindow) == null){
                                serverProperties.set(OWLHTMLProperty.optionContentWindow, OWLHTMLConstants.LinkTarget.content.toString());
                                success = true;
                            }
                        }
                        break;
                    default:
                        success = serverProperties.set(property, value);
                }
            }
            catch(IllegalArgumentException e){
                // this will be an OWL server preference
                try{
                    ServerProperty property = ServerProperty.valueOf(propertyName);
                    success = kit.getOWLServer().getProperties().set(property, value);
                }
                catch(IllegalArgumentException e2){
                    throw new OntServerException("Cannot set unknown property: " + propertyName);
                }
            }

            if (success){
                SessionManager.labelServerState(kit);
            }
        }
        return success;
    }

    protected Map<OWLHTMLParam, Set<String>> getRequiredParams(OWLServer server) {
        return Collections.emptyMap();
    }
}
