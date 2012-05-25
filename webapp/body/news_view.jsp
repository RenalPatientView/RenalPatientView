<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:xhtml/>

 <logic:present name="news">

    <table cellpadding="3" width="90%">
      <tr>
        <td><b><bean:write name="news" property="headline" /></b></td>
        <td align="right" valign="center"><bean:write name="news" property="formattedDatestamp"/></td>
      </tr>
      <tr>
        <td colspan="2" ><bean:write name="news" property="bodyForHtml" filter="false"/></td>
      </tr>
    </table>

</logic:present>