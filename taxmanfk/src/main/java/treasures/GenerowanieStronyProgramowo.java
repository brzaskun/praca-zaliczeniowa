/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package treasures;

/**
 *
 * @author Osito
 */
public class GenerowanieStronyProgramowo {
    //    public void wygenerujSTRKolumne() {
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        FacesContext facesCtx = FacesContext.getCurrentInstance();
//        ELContext elContext = facesCtx.getELContext();
//        grid3 = getGrid3();
//        grid3.getChildren().clear();
//        PrimeFaces.current().ajax().update("dodWiad:grid3");
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//        HtmlOutputText ot = new HtmlOutputText();
//        ot.setValue("nazwa Srodka");
//        grid3.getChildren().add(ot);
//        HtmlInputText zdefiniowaneEwidencje = new HtmlInputText();
//        final String binding = "#{DokumentView.nazwaSTR}";
//        ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
//        zdefiniowaneEwidencje.setValueExpression("value", ve2);
//        zdefiniowaneEwidencje.setId("nazwasrodka");
//        zdefiniowaneEwidencje.setAccesskey("t");
//        grid3.getChildren().add(zdefiniowaneEwidencje);
//
//        HtmlOutputText ot1 = new HtmlOutputText();
//        ot1.setValue("data przyjecia");
//        grid3.getChildren().add(ot1);
//        HtmlInputText ew1 = new HtmlInputText();
//        final String binding1 = "#{DokumentView.dataPrzSTR}";
//        ValueExpression ve1 = ef.createValueExpression(elContext, binding1, String.class);
//        ew1.setValueExpression("value", ve1);
//        ew1.setId("dataprz");
//        ew1.setOnblur("ustawDateSrodekTrw();");
//        grid3.getChildren().add(ew1);
//
//        HtmlOutputText ot3 = new HtmlOutputText();
//        ot3.setValue("symbol KST");
//        grid3.getChildren().add(ot3);
//        HtmlInputText ew3 = new HtmlInputText();
//        final String binding3 = "#{DokumentView.symbolKST}";
//        ValueExpression ve3 = ef.createValueExpression(elContext, binding3, String.class);
//        ew3.setValueExpression("value", ve3);
//        ew3.setId("symbolKST");
//        grid3.getChildren().add(ew3);
//
////            HtmlOutputText ot4 = new HtmlOutputText();
////            ot4.setValue("wybierz kategoria");
////            grid3.getChildren().add(ot4);
//
////            "id="acForce" value="#{DokumentView.selDokument.kontr}" completeMethod="#{KlView.complete}"
////                                    var="p" itemLabel="#{p.npelna}" itemValue="#{p}" converter="KlientConv" 
////                                    minQueryLength="3" maxResults="10" maxlength="10" converterMessage="Nieudana konwersja Klient"  forceSelection="true" 
////                                    effect="clip"  binding="#{DokumentView.kontrahentNazwa}" valueChangeListener="#{DokumentView.przekazKontrahenta}" 
////                                    required="true" requiredMessage="Pole klienta nie może byc puste" queryDelay="100" onblur="validateK()">
////             "                               
////            AutoComplete autoComplete = new AutoComplete();
////            final String bindingY = "#{DokumentView.srodekkategoria}";
////            ValueExpression ve2Y = ef.createValueExpression(elContext, bindingY, String.class);
////            autoComplete.setValueExpression("value", ve2Y);
////            autoComplete.setVar("p");
////            autoComplete.setItemLabel("#{p.nazwa}");
////            autoComplete.setItemValue("#{p.nazwa}");
////            autoComplete.setMinQueryLength(3);
////            FacesContext context = FacesContext.getCurrentInstance();
////            MethodExpression actionListener = context.getApplication().getExpressionFactory()
////    .createMethodExpression(context.getELContext(), "#{srodkikstView.complete}", null, new Class[] {ActionEvent.class});
////            autoComplete.setCompleteMethod(actionListener);
////            grid3.getChildren().add(autoComplete);
////            
//
//        HtmlOutputText ot4 = new HtmlOutputText();
//        ot4.setValue("stawka amort");
//        grid3.getChildren().add(ot4);
//        HtmlInputText ew4 = new HtmlInputText();
//        final String binding4 = "#{DokumentView.stawkaKST}";
//        ValueExpression ve4 = ef.createValueExpression(elContext, binding4, String.class);
//        ew4.setValueExpression("value", ve4);
//        ew4.setId("stawkaKST");
//        grid3.getChildren().add(ew4);
//
//        HtmlOutputText ot5 = new HtmlOutputText();
//        ot5.setValue("dotychczasowe umorzenie");
//        grid3.getChildren().add(ot5);
//        InputNumber ew5 = new InputNumber();
//        final String binding5 = "#{DokumentView.umorzeniepoczatkowe}";
//        ValueExpression ve5 = ef.createValueExpression(elContext, binding5, String.class);
//        ew5.setValueExpression("value", ve5);
//        ew5.setSymbol(" zł");
//        ew5.setSymbolPosition("s");
//        ew5.setDecimalPlaces(".");
//        ew5.setThousandSeparator(" ");
//        ew5.setDecimalPlaces("2");
//        ew5.setValue(0);
//        grid3.getChildren().add(ew5);
//        umorzeniepoczatkowe = 0.0;
//
//        UISelectItems ulistaX = new UISelectItems();
//        List valueListX = new ArrayList();
//        SelectItem selectItem = new SelectItem("srodek trw.", "srodek trw.");
//        valueListX.add(selectItem);
//        selectItem = new SelectItem("wyposazenie", "wyposazenie");
//        valueListX.add(selectItem);
//        ulistaX.setValue(valueListX);
//        final String bindingX = "#{DokumentView.typKST}";
//        ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
//        HtmlSelectOneMenu htmlSelectOneMenuX = new HtmlSelectOneMenu();
//        htmlSelectOneMenuX.setValueExpression("value", ve2X);
//        htmlSelectOneMenuX.setStyle("min-width: 150px");
//        htmlSelectOneMenuX.getChildren().add(ulistaX);
//        grid3.getChildren().add(htmlSelectOneMenuX);
//
//        PrimeFaces.current().ajax().update("dodWiad:grid3");
//    }
}
