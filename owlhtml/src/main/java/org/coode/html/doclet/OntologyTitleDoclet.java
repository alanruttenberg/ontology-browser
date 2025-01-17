package org.coode.html.doclet;

import java.io.PrintWriter;
import java.net.URL;
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

import org.coode.html.OWLHTMLKit;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br>
 * <br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Aug 5, 2009<br>
 * <br>
 */
public class OntologyTitleDoclet extends AbstractTitleDoclet<OWLOntology> {

    public OntologyTitleDoclet(OWLHTMLKit kit) {
        super(kit);
    }

    @Override
    protected void renderHeader(URL pageURL, PrintWriter out) {
        super.renderHeader(pageURL, out);
        IRI docIRI = getOWLHTMLKit().getOWLServer().getOWLOntologyManager().getOntologyDocumentIRI(getUserObject());
        //        if (!docIRI.equals(getUserObject().getOntologyID().getDefaultDocumentIRI())) {
        //            out.println("<h3>Loaded from " + docIRI + "</h3>");
        //}
    }

    @Override
    public String getTitle() {
        return getOWLHTMLKit().getOWLServer().getOntologyShortFormProvider().getShortForm(getUserObject());
    }

    @Override
    public String getSubtitle() {
        if (getUserObject().isAnonymous()) {
            return null;
        }
        String s = "Ontology IRI: " + getUserObject().getOntologyID().getOntologyIRI().get().toString();
        IRI versionIRI = getUserObject().getOntologyID().getVersionIRI().orNull();
        if (versionIRI != null) {
            s += "<br />" + "Version IRI: " + versionIRI.toString();
        }
        return s;
    }
}
