<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/datetime" prefix="dt" %>

<html:xhtml/>

<p class="header">GP</p>


On <dt:format pattern="d MMM yyyy"><dt:currentTime/></dt:format> you successfully enrolled a new GP with the following details:
<br /><br />

<table cellpadding="3" >
    <tr>
      <td><b>User Name</b></td>
      <td><bean:write name="gp" property="username" /></td>
    </tr>
    <tr>
      <td><b>Password</b></td>
      <td class="password"><bean:write name="gp" property="password" /></td>
    </tr>
    <tr>
      <td><b>Name</b></td>
      <td><bean:write name="gp" property="name" /></td>
    </tr>
    <tr>
      <td><b>NHS Number</b></td>
      <td><bean:write name="gp" property="nhsno" /></td>
    </tr>
    <tr>
      <td><b>Email Address</b></td>
      <td><bean:write name="gp" property="email" /></td>
    </tr>
 </table>

