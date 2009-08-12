package org.coode.www.doclet;

/**
 * Author: drummond<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jul 11, 2006<br><br>
 * <p/>
 * nick.drummond@cs.manchester.ac.uk<br>
 * www.cs.man.ac.uk/~drummond<br><br>
 *
 * Creates the HTML for a restriction form element (3 dropdown selectors for property, type and filler)
 */
public class ConjunctDoclet{//{ extends AbstractHTMLDoclet<OWLQuantifiedRestriction> {
//
//    private static final Logger logger = Logger.getLogger(ConjunctDoclet.class);
//
//    private static final String ID = "doclet.conjunct";
//
//    private static final String REMOVE_JS_ACTION = "removeFromForm(this.parentNode);" +
//                                                   "sendQuery(getCurrentQuery()); " +
//                                                   "setAddVisible(true);";
//
//    private static final String PROP_CHANGE_JS_ACTION = "requestFillerConstraints(this); sendQuery(getCurrentQuery());";
//
//    private static final String TYPE_CHANGE_JS_ACTION = "requestFillerConstraints(this); sendQuery(getCurrentQuery()); ";
//
//    private static final String FILLER_CHANGE_JS_ACTION = "setAddVisible(true);" +
//                                                          "sendQuery(getCurrentQuery());";
//
//    private Set<OWLProperty> properties;
//    private Set<OWLPropertyRange> fillers;
//    private OWLProperty property;
//    private OWLPropertyRange filler;
//    private String type;
//
//    private boolean renderRemoveButton = false;
//    private boolean renderAllTypes = true;
//    private OWLHTMLKit kit;
//
//
//    public ConjunctDoclet(OWLHTMLKit kit) throws Exception {
//        this.kit = kit;
//        properties = new HashSet<OWLProperty>();
//        fillers = new HashSet<OWLPropertyRange>();
//
//        for (OWLOntology ont: kit.getOntologies()){
//
//            // get the properties
//            properties.addAll(ont.getReferencedObjectProperties());
//            properties.addAll(ont.getReferencedDataProperties());
//
//            // and all of the fillers
//            fillers.addAll(ont.getReferencedClasses());
//        }
//    }
//
//    public ConjunctDoclet(Set<OWLProperty> properties, Set<OWLPropertyRange> fillers, OWLHTMLKit kit) {
//        this.kit = kit;
//        this.properties = properties;
//        this.fillers = fillers;
//    }
//
//    public ConjunctDoclet(OWLQuantifiedRestriction op, OWLHTMLKit kit) {
//        this.kit = kit;
//
//        if (op.getProperty() instanceof OWLObjectProperty &&
//            op.getFiller() instanceof OWLClass){
//
//            if (op instanceof OWLObjectSomeValuesFrom){
//                type = ServerConstants.SOME;
//            }
//            else if (op instanceof OWLObjectAllValuesFrom){
//                type = ServerConstants.ONLY;
//            }
//
//            property = (OWLObjectProperty)op.getProperty();
//            filler = (OWLClass)op.getFiller();
//
//            fillers = kit.getClassHierarchyProvider().getDescendants(filler);
//            fillers.add(filler);
//
//            properties = new HashSet<OWLProperty>();
//            properties.add(property);
//            for (OWLObjectPropertyExpression objProp : ((OWLObjectProperty)property).getSubProperties(kit.getOntologies())){
//                if (objProp instanceof OWLObjectProperty){
//                    properties.add((OWLObjectProperty)objProp);
//                }
//            }
//        }
//    }
//
//    public void setRenderRemoveButton(boolean render){
//        renderRemoveButton = render;
//    }
//
//    public String getTitle() {
//        return null;
//    }
//
//    public void setSelectedProperty(OWLProperty prop){
//        this.property = prop;
//    }
//
//    public void setSelectedFiller(OWLPropertyRange filler){
//        this.filler = filler;
//    }
//
//    public void setRestrictionType(String type){
//        this.type = type;
//    }
//
//    public void setAutoRenderAllTypes(boolean renderAllTypes){
//        this.renderAllTypes = renderAllTypes;
//    }
//
//
//    protected void renderHeader(URL pageURL, PrintWriter out) {
//
//        final ShortFormProvider entityRen = kit.getShortFormProvider();
//        OWLHTMLRenderer htmlRen = new OWLHTMLRenderer(kit);
//        htmlRen.setContentTargetWindow(OWLHTMLConstants.LinkTarget.content);
//
//        if (properties.size() > 1){
//            out.println("and <select name='property' onchange='" + PROP_CHANGE_JS_ACTION + "'>");
//            // @@TODO below is not how doclets are to be used
//            new SelectorContentsDoclet(properties, property, entityRen, kit).renderAll(pageURL, out);
//            out.println("</select>");
//        }
//        else if (properties.size() == 1){
//            out.println("and ");
//            out.println("<input type='hidden' name='property' value='" + properties.iterator().next() + "'/>");
//            htmlRen.render(properties.iterator().next(), pageURL, out);
//            out.print(" ");
//        }
//
//        if (type == null || renderAllTypes){
//            out.print("<select name='type' onchange='" + TYPE_CHANGE_JS_ACTION + "'>");
//            for (String currentType : ServerConstants.RESTRICTION_TYPES){
//                out.println("<option value='" + currentType + "'");
//                if (currentType.equals(type)){
//                    out.print(" selected='selected' ");
//                }
//                out.println(">" + currentType + "</option>");
//            }
//            out.println("</select>");
//        }
//        else{
//            out.println(" <input type='hidden' name='type' value='" + type + "'/>" + type ); // must be inside some element
//        }
//
//        if (fillers.size() == 1){
//            out.println("<input type='hidden' name='value' value='" + fillers.iterator().next() + "'/>");
//            out.print(" ");
//            htmlRen.render(fillers.iterator().next(), pageURL, out);
//        }
//        else {
//            out.println("<select name='value' onchange='" + FILLER_CHANGE_JS_ACTION + "'>");
//            // @@TODO below is not how doclets are to be used
//            new SelectorContentsDoclet(fillers, filler, entityRen, kit).renderAll(pageURL, out);
//            out.println("</select>");
//        }
//
//        if (renderRemoveButton){
//            try {
//                URL imageURL = new URL(kit.getBaseURL(), OWLHTMLConstants.IMAGES_REMOVE_PNG);
//                out.println("<img class='button' src='" + URLUtils.createRelativeURL(pageURL, imageURL) +
//                            "' width='24' height='24' onmouseup='" + REMOVE_JS_ACTION + "' />");
//            }
//            catch (MalformedURLException e) {
//                logger.error("Could not find image for remove button: " + OWLHTMLConstants.IMAGES_REMOVE_PNG, e);
//            }
//        }
//    }
//
//    protected void renderFooter(URL pageURL, PrintWriter out) {
//        // do nothing
//    }
//
//    public String getID() {
//        return ID;
//    }
}
