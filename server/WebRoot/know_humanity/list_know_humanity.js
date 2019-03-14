var reloadList = function() {
	layui.use([ 'layer' ], function() {
		var $ = layui.juery, layer = layui.layer;
		layer.closeAll();
		loadList(true);
	});
}
layui.use('layer', function() {
	var $ = layui.juery, layer = layui.layer;
	var index = '';
	reloadList(true);

});
var resetSwClose = function(isClose) {
	layui.use([ 'layer' ], function() {
		var $ = layui.jquery, layer = layui.layer;// 独立版的layer无需执行这一句
		if (isClose) {
			$('.layui-layer-setwin a.layui-layer-close1').hide();
		} else {
			$('.layui-layer-setwin a.layui-layer-close1').show();
		}
	});
}

var loadList = function(first) {
	layui.config({
				base : "js/"
			})
			.use([ 'form', 'layer', 'jquery', 'laypage', 'table' ],
					function() {
						var form = layui.form, layer = layui.layer, laypage = layui.laypage, $ = layui.jquery;
						var table = layui.table;
						function getUParam(name, id) {
							var reg = new RegExp("(^|&)" + name
									+ "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
							var r = decodeURIComponent(
									$("#" + id).attr("src").substr(
											$("#" + id).attr("src")
													.indexOf("?")).substr(1))
									.match(reg); // 匹配目标参数
							if (r != null)
								return unescape(r[2]);
							return ""; // 返回参数值
						}
						var ctxPath = getUParam("ctx", "listjs");
						// 第一个实例
						table.render({
							elem : '#list',
							url : ctxPath + '/know_humanity/getAllKownledgeHumanity' // 数据接口
							,
							cols : [ [ // 表头
							{
								type : 'checkbox',
								fixed : 'left'
							}, {
								field : 'id',
								title : 'ID',
								sort : true,
								fixed : 'left'
							}, {
								field : 'key',
								title : '问题关键字'
							},{
								field : 'answer',
								title : '参考答案'
							}
							,{
								field : 'probably_answer',
								title : '相似参考答案'
							}
							,{
								field : 'label',
								title : '标签'
							}
							,{
								field : 'first_classification',
								title : '一级分类'
							}
							,{
								field : 'second_classification',
								title : '二级分类'
							}
							,{
								field : 'remark',
								title : '备注'
							}
							, {
								fixed : 'right',
								title : '操作',
								width : 160,
								templet : '#listtable-opt',
								align : 'center'
							} ] ],
							page : true,
							limits : [ 40, 60, 80, 100, 120, 140 ],
							height : 'full-80'
						// 开启分页
						});
						// 监听工具条
						table.on('tool(listtable)', function(obj) {
							var that = this;
							var data = obj.data;
							var idDatas="id="+data.id;
							if (obj.event === 'del') {
								layer.confirm('你确认删除这条数据吗?', function(index) {
									$.getJSON(ctxPath + "/know_humanity/deleteKownledgeHumanity",
											idDatas, function(jsondata) {
												if (jsondata.code == '200') {
													layer.msg('删除数据成功', {
														time : 1000,
														shade : [ 0.001,
																'#ffffff' ]
													}, function() {
														reloadList();
													});
												} else {
													layer.msg(jsondata.msg, {
														time : 2000
													});
												}
											});
								});
							} else if (obj.event === 'edit') {
								if ($(that).attr("disabled") == "disabled")
									return;
								// 执行重载
								layer.open({
									title : [ '修改信息' ],
									type : 2,
									area : [ '800px', '700px' ],
									shade : [ 0.7, '#d0d7f6' ],
									scrollbar : true,
									maxmin : false,
									fixed : true,
									move : false,
									content : [
											ctxPath + '/know_humanity/toHtmlModify?id='
													+ data.id, 'no' ],
									end : function() {
									}
								});
							}else if(obj.event == 'pl'){
								if ($(that).attr("disabled") == "disabled")
									return;
								// 执行重载
								layer.open({
									title : [ '评论信息' ],
									type : 2,
									area : [ '100%', '100%' ],
									shade : [ 0.7, '#d0d7f6' ],
									scrollbar : true,
									maxmin : false,
									fixed : true,
									move : false,
									content : [
											ctxPath + '/pl/tolist', 'no' ],
									end : function() {
									}
								});
							}
						});

						var $ = layui.$, active = {
								add : function() { // 获取选中数据
								index = layer.open({
									title : "添加",
									type : 2,
									area : [ '100%', '100%' ],
									content : ctxPath + "/know_humanity/toHtmlAdd"
								});
							},
							dels : function() { // 获取选中数目
								var checkStatus = table.checkStatus('list'), data = checkStatus.data;
								var len = data.length;
								var idDatas = "";
								for ( var i = 0, l = len; i < l; i++) {
									if (i == 0) {
										idDatas += "id=" + data[i].id
									} else {
										idDatas += "&id=" + data[i].id
									}
								}
								if (len == 0) {
									layer.msg('请选择您将要删除的记录', {
										time : 2000
									});
									return false;
								} else {
									var info = '些';
									if (len == 1)
										info = '条';
									layer
											.confirm(
													'你确认删除这' + info + '记录吗？',
													{
														btn : [ '确认', '取消' ]
													// 按钮
													},
													function(index) {
														$.getJSON(
																		ctxPath
																				+ "/know_humanity/deleteKownledgeHumanityList",
																		idDatas,
																		function(
																				jsondata) {
																			if (jsondata.code == '200') {
																				layer.msg(
																								'删除数据成功',
																								{
																									time : 1000,
																									shade : [
																											0.001,
																											'#ffffff' ]
																								},
																								function() {
																									reloadList();
																								});
																			} else {
																				layer
																						.msg(
																								jsondata.msg,
																								{
																									time : 2000
																								});
																			}
																		});
													});
								}
							},
							del : function() { // 验证是否全选
								var checkStatus = table.checkStatus('idTest');
								layer.msg(checkStatus.isAll ? '全选' : '未全选')
							}
						};
						
						$('.layui-btn').on('click', function() {
							var type = $(this).data('type');
							active[type] ? active[type].call(this) : '';
						});
						
						
						
						// 全选
						form.on('checkbox(allChoose)',
										function(data) {
											var child = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"])');
											child
													.each(function(index, item) {
														item.checked = data.elem.checked;
													});
											form.render('checkbox');
										});

						// 通过判断文章是否全部选中来确定全选按钮是否选中
						form.on("checkbox(choose)",
										function(data) {
											var child = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"])');
											var childChecked = $(data.elem)
													.parents('table')
													.find(
															'tbody input[type="checkbox"]:not([name="show"]):checked')
											if (childChecked.length == child.length) {
												$(data.elem)
														.parents('table')
														.find(
																'thead input#allChoose')
														.get(0).checked = true;
											} else {
												$(data.elem)
														.parents('table')
														.find(
																'thead input#allChoose')
														.get(0).checked = false;
											}
											form.render('checkbox');
										});
					});
}
