<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/datetime" prefix="dt" %>

<html:xhtml/>

<p class="header">Patient</p>


There is already a unit user registered on RPV with that user name but in another unit.

Please carefully check the details of that user, then you can choose to add the existing user to your unit.
<br /><br />


<table cellpadding="3" >
    <tr>
      <td><b>User Name</b></td>
      <td><bean:write name="usermapping" property="username"/></td>
    </tr>
    <tr>
      <td><b>NHS Number</b></td>
      <td><bean:write name="usermapping" property="unitcode" /></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <html:form action="/control/unitAdminAddToUnit">
        <html:hidden name="usermapping" property="username"/>
        <html:hidden name="usermapping" property="unitcode"/>
        <td colspan="2"><html:submit value="Add to Unit" styleClass="formbutton" /></td>
      </html:form>
    </tr>
 </table>


<br />

