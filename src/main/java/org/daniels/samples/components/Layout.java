package org.daniels.samples.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.daniels.samples.constants.AppSymbolConstants;


/**
 * Layout component for pages of application tapestry5-hotel-booking.
 */
//@Import(stylesheet =
//{ "context:/static/style.css" }, library =
//{ "context:/static/hotel-booking.js" })



@Import(stack = AppSymbolConstants.BOOTSTRAP_STACK)
public class Layout
{
    @Property
    private String pageName;

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @SuppressWarnings("unused")
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;



    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
    }



}
