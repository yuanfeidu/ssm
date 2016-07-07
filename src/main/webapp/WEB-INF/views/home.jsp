<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/easyui/themes/default/easyui.css'/>">
<link type="text/css" rel="stylesheet" href="<c:url value='/easyui/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/easyui/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/easyui/jquery.easyui.min.js'/>"></script>
<title>首页</title>
</head>
<body>
	<div class="easyui-panel" style="width: 100%;height: 700px;" title="preview">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height: 100px"></div> 
			<div data-options="region:'south'" style="height: 80px"></div>
			<!-- <div data-options="region:'east',title:'东部',split:true" style="width: 100px"></div> -->
			<div data-options="region:'west',title:'主菜单'" style="width: 200px">
				<!-- add accordion -->
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="菜单1" data-options="selected:true">
						<ul class="easyui-tree " data-options="url:'easyui/tree1.json',method:'get',animate:true"></ul>
					</div>
					<div title="菜单2">菜单2</div>
					<div title="菜单3">菜单3</div>
				</div>
			</div>
			<div data-options="region:'center'" style="padding: 5px">
				<!-- add tab -->
				<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="about" style="padding: 10px">欢迎 ${username}!</div>
					<div title="DataGrid" style="padding:5px">
						<table class="easyui-datagrid" data-options="url:'easyui/ttt.json',method:'get',singleSelect:true,fit:true,fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'id'" width="80">id</th>
									<th data-options="field:'name'" width="100">name</th>
									<th data-options="field:'phone'" width="120">phone</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer" class="units-row text-centered" style="margin-left:45%">Copyright © 2016-2017</div>
</body>
</html>