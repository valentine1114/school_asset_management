"use strict";(self["webpackChunktemplate"]=self["webpackChunktemplate"]||[]).push([[5885,3563,8494],{3563:(t,e,s)=>{s.r(e),s.d(e,{default:()=>m});var a=function(){var t=this,e=t._self._c;return e("div",[e("Card",[e("div",{attrs:{slot:"title"},slot:"title"},[e("div",{staticClass:"edit-head"},[e("a",{staticClass:"back-title",on:{click:t.close}},[e("Icon",{attrs:{type:"ios-arrow-back"}}),t._v("返回 ")],1),e("div",{staticClass:"head-name"},[t._v("添加")]),e("span"),e("a",{staticClass:"window-close",on:{click:t.close}},[e("Icon",{staticClass:"ivu-icon-ios-close",attrs:{type:"ios-close",size:"31"}})],1)])]),e("Form",{ref:"form",attrs:{model:t.form,"label-width":100,rules:t.formValidate,"label-position":"left"}},[e("FormItem",{attrs:{label:"名称",prop:"name"}},[e("Input",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),e("FormItem",{attrs:{label:"备注",prop:"remark"}},[e("Input",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.remark,callback:function(e){t.$set(t.form,"remark",e)},expression:"form.remark"}})],1),e("FormItem",{attrs:{label:"状态",prop:"status"}},[e("Select",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.status,callback:function(e){t.$set(t.form,"status",e)},expression:"form.status"}},[e("Option",{attrs:{value:"0"}},[t._v("正常")]),e("Option",{attrs:{value:"1"}},[t._v("封存")])],1)],1),e("Form-item",{staticClass:"br"},[e("Button",{attrs:{loading:t.submitLoading,type:"primary"},on:{click:t.handleSubmit}},[t._v("提交并保存")]),e("Button",{on:{click:t.handleReset}},[t._v("重置")]),e("Button",{attrs:{type:"dashed"},on:{click:t.close}},[t._v("关闭")])],1)],1)],1)],1)},i=[],l=s(7722);const o={name:"add",components:{},data(){return{submitLoading:!1,form:{name:"",remark:"",status:"0"},formValidate:{}}},methods:{init(){},handleReset(){this.$refs.form.resetFields()},handleSubmit(){this.$refs.form.validate((t=>{t&&(0,l.N)(this.form).then((t=>{this.submitLoading=!1,t.success&&(this.$Message.success("操作成功"),this.submited())}))}))},close(){this.$emit("close",!0)},submited(){this.$emit("submited",!0)}},mounted(){this.init()}},r=o;var n=s(1001),c=(0,n.Z)(r,a,i,!1,null,null,null);const m=c.exports},8494:(t,e,s)=>{s.r(e),s.d(e,{default:()=>m});var a=function(){var t=this,e=t._self._c;return e("div",[e("Card",[e("div",{attrs:{slot:"title"},slot:"title"},[e("div",{staticClass:"edit-head"},[e("a",{staticClass:"back-title",on:{click:t.close}},[e("Icon",{attrs:{type:"ios-arrow-back"}}),t._v("返回 ")],1),e("div",{staticClass:"head-name"},[t._v("编辑")]),e("span"),e("a",{staticClass:"window-close",on:{click:t.close}},[e("Icon",{staticClass:"ivu-icon-ios-close",attrs:{type:"ios-close",size:"31"}})],1)])]),e("Form",{ref:"form",attrs:{model:t.form,"label-width":100,rules:t.formValidate,"label-position":"left"}},[e("FormItem",{attrs:{label:"名称",prop:"name"}},[e("Input",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),e("FormItem",{attrs:{label:"备注",prop:"remark"}},[e("Input",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.remark,callback:function(e){t.$set(t.form,"remark",e)},expression:"form.remark"}})],1),e("FormItem",{attrs:{label:"状态",prop:"status"}},[e("Select",{staticStyle:{width:"570px"},attrs:{clearable:""},model:{value:t.form.status,callback:function(e){t.$set(t.form,"status",e)},expression:"form.status"}},[e("Option",{attrs:{value:"0"}},[t._v("正常")]),e("Option",{attrs:{value:"1"}},[t._v("封存")])],1)],1),e("Form-item",{staticClass:"br"},[e("Button",{attrs:{loading:t.submitLoading,type:"primary"},on:{click:t.handleSubmit}},[t._v("提交并保存")]),e("Button",{on:{click:t.handleReset}},[t._v("重置")]),e("Button",{attrs:{type:"dashed"},on:{click:t.close}},[t._v("关闭")])],1)],1)],1)],1)},i=[],l=s(7722);const o={name:"edit",components:{},props:{data:Object},data(){return{submitLoading:!1,form:{name:"",remark:"",status:""},formValidate:{}}},methods:{init(){this.handleReset(),this.form=this.data,0==this.form.status?this.form.status="0":this.form.status="1"},handleReset(){this.$refs.form.resetFields()},handleSubmit(){this.$refs.form.validate((t=>{t&&(0,l.QX)(this.form).then((t=>{this.submitLoading=!1,t.success&&(this.$Message.success("操作成功"),this.submited())}))}))},close(){this.$emit("close",!0)},submited(){this.$emit("submited",!0)}},mounted(){this.init()}},r=o;var n=s(1001),c=(0,n.Z)(r,a,i,!1,null,null,null);const m=c.exports},5885:(t,e,s)=>{s.r(e),s.d(e,{default:()=>h});var a=function(){var t=this,e=t._self._c;return e("div",{staticClass:"search"},["add"==t.currView?e("add",{on:{close:function(e){t.currView="index"},submited:t.submited}}):t._e(),"edit"==t.currView?e("edit",{attrs:{data:t.formData},on:{close:function(e){t.currView="index"},submited:t.submited}}):t._e(),e("Card",{directives:[{name:"show",rawName:"v-show",value:"index"==t.currView,expression:"currView == 'index'"}]},[e("Row",{directives:[{name:"show",rawName:"v-show",value:t.openSearch,expression:"openSearch"}],nativeOn:{keydown:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleSearch.apply(null,arguments)}}},[e("Form",{ref:"searchForm",attrs:{model:t.searchForm,inline:"","label-width":0}},[e("Form-item",{ref:"searchForm",staticStyle:{display:"flex"},attrs:{model:t.searchForm,inline:"","label-width":0}},[e("Form-item",{attrs:{label:"",prop:"name"}},[e("Input",{staticStyle:{width:"200px"},attrs:{type:"text",placeholder:"请输入名称",clearable:""},model:{value:t.searchForm.name,callback:function(e){t.$set(t.searchForm,"name",e)},expression:"searchForm.name"}})],1),e("Form-item",{staticClass:"br",staticStyle:{"margin-left":"10px"}},[e("Button",{attrs:{type:"primary",icon:"ios-search",size:"small",ghost:"",shape:"circle"},on:{click:t.handleSearch}},[t._v("搜索")]),e("Button",{attrs:{type:"warning",size:"small",ghost:"",shape:"circle",icon:"md-refresh"},on:{click:t.handleReset}},[t._v("重置")]),e("Button",{attrs:{type:"info",size:"small",ghost:"",shape:"circle",icon:"md-add"},on:{click:t.add}},[t._v("新增")]),e("Button",{attrs:{type:"error",icon:"md-trash",size:"small",ghost:"",shape:"circle"},on:{click:t.delAll}},[t._v("删除")]),e("Button",{attrs:{type:"success",icon:"md-paper-plane",size:"small",ghost:"",shape:"circle"},on:{click:t.excelData}},[t._v("导出")])],1),e("Form-item",{staticStyle:{position:"fixed",right:"50px",top:"110px"}},[e("Poptip",{attrs:{trigger:"hover",content:"自定义表格的显示列,适应屏幕显示",placement:"left"}},[e("Button",{staticClass:"showFilterPanelFlag",attrs:{type:"info",icon:"ios-settings",size:"small",ghost:""},on:{click:function(e){t.showFilterPanelFlag=!t.showFilterPanelFlag}}},[t._v("列筛选 ")])],1),e("Poptip",{attrs:{trigger:"hover",content:"点我查看本模块的使用教程",placement:"left"}},[e("Button",{staticClass:"showFilterPanelFlag",attrs:{type:"warning",icon:"md-help",size:"small",ghost:""},on:{click:function(e){t.modal1=!0}}},[t._v("使用教程 ")])],1),e("Modal",{attrs:{title:"使用教程"},model:{value:t.modal1,callback:function(e){t.modal1=e},expression:"modal1"}},[e("p",[t._v("1.XXXXXXXXXXXXXXXXXXXXXXXX")]),e("p",[t._v("2.XXXXXXXXXXXXXXXXXXXXXXXX")]),e("p",[t._v("3.XXXXXXXXXXXXXXXXXXXXXXXX")])])],1)],1)],1)],1),e("Row",{staticClass:"operation",staticStyle:{position:"relative"}},[e("transition",[e("div",{directives:[{name:"show",rawName:"v-show",value:t.showFilterPanelFlag,expression:"showFilterPanelFlag"}],staticClass:"filter-panel"},[e("CheckboxGroup",{model:{value:t.selected,callback:function(e){t.selected=e},expression:"selected"}},t._l(t.mycolumns,(function(t){return e("div",{key:t.key},[e("Checkbox",{staticStyle:{margin:"2px 5px"},attrs:{label:t.title}})],1)})),0)],1)])],1),e("Row",{directives:[{name:"show",rawName:"v-show",value:t.openTip,expression:"openTip"}]}),e("Row",[e("Table",{ref:"table",attrs:{loading:t.loading,border:"",columns:t.columns,data:t.data,sortable:"custom","row-class-name":t.rowClassNmae},on:{"on-sort-change":t.changeSort,"on-selection-change":t.changeSelect,"on-row-click":t.rowClick}})],1),e("Row",{staticClass:"page",attrs:{type:"flex",justify:"end"}},[e("Page",{attrs:{current:t.searchForm.pageNumber,total:t.total,"page-size":t.searchForm.pageSize,"page-size-opts":[10,20,50],size:"small","show-total":"","show-elevator":"","show-sizer":""},on:{"on-change":t.changePage,"on-page-size-change":t.changePageSize}})],1)],1)],1)},i=[],l=s(7722),o=s(3563),r=s(8494);const n={name:"single-window",components:{add:o["default"],edit:r["default"]},data(){return{selected:["选择","序号","编码","名称","备注","状态","创建时间","创建者","修改时间","修改者","操作"],modal1:!1,openSearch:!0,openTip:!0,formData:{},currView:"index",loading:!0,searchForm:{pageNumber:1,pageSize:10,sort:"createTime",order:"desc"},selectList:[],selectCount:0,selectRow:0,columns:[{type:"selection",width:60,title:"选择",align:"center",fixed:"left"},{title:"序号",width:85,align:"center",fixed:"left",sortType:!0,render:(t,e)=>t("span",e.index+(this.searchForm.pageNumber-1)*this.searchForm.pageSize+1)},{title:"编码",key:"bm",minWidth:120,sortable:!0,align:"center",tooltip:!0},{title:"名称",key:"name",minWidth:120,sortable:!0,align:"center",tooltip:!0},{title:"备注",key:"remark",minWidth:120,sortable:!0,align:"center",tooltip:!0},{title:"状态",key:"status",minWidth:120,sortable:!0,align:"center",tooltip:!0,render:(t,e)=>0==e.row.status?t("div",[t("span",{style:{color:"#111111"}},"正常")]):t("div",[t("span",{style:{color:"#ff0000"}},"封存")])},{title:"创建时间",key:"createTime",sortable:!0,sortType:"desc",minWidth:180,sortable:!0,align:"center",tooltip:!0},{title:"创建者",key:"createBy",sortable:!0,sortType:"desc",minWidth:100,sortable:!0,align:"center",tooltip:!0},{title:"修改时间",key:"updateTime",sortable:!0,sortType:"desc",minWidth:180,sortable:!0,align:"center",tooltip:!0},{title:"修改者",key:"updateBy",sortable:!0,sortType:"desc",minWidth:100,sortable:!0,align:"center",tooltip:!0},{title:"操作",key:"action",align:"center",width:200,render:(t,e)=>t("div",[t("Button",{props:{type:"primary",size:"small",ghost:!0,icon:"ios-create-outline"},style:{marginRight:"5px"},on:{click:()=>{this.edit(e.row)}}},"编辑"),t("Button",{props:{type:"error",size:"small",ghost:!0,icon:"md-trash"},on:{click:()=>{this.remove(e.row)}}},"删除")])}],data:[],pageNumber:1,pageSize:10,total:0,showFilterPanelFlag:!1}},methods:{init(){this.getDataList()},submited(){this.currView="index",this.getDataList()},changePage(t){this.searchForm.pageNumber=t,this.getDataList(),this.clearSelectAll()},changePageSize(t){this.searchForm.pageSize=t,this.getDataList()},rowClick(t,e){this.selectRow=t},rowClassNmae(t,e){return t.id==this.selectRow.id?"rowClassNmaeColor":""},excelData(){this.$refs.table.exportCsv({filename:"导出结果"})},handleSearch(){this.searchForm.pageNumber=1,this.searchForm.pageSize=10,this.getDataList()},handleReset(){this.$refs.searchForm.resetFields(),this.searchForm.pageNumber=1,this.searchForm.pageSize=10,this.getDataList()},changeSort(t){this.searchForm.sort=t.key,this.searchForm.order=t.order,"normal"===t.order&&(this.searchForm.order=""),this.getDataList()},clearSelectAll(){this.$refs.table.selectAll(!1)},changeSelect(t){this.selectList=t,this.selectCount=t.length},getDataList(){this.loading=!0,(0,l.lI)(this.searchForm).then((t=>{this.loading=!1,t.success&&(this.data=t.result.records,this.total=t.result.total)}))},add(){this.currView="add"},edit(t){for(let a in t)null==t[a]&&(t[a]="");let e=JSON.stringify(t),s=JSON.parse(e);this.formData=s,this.currView="edit"},remove(t){this.$Modal.confirm({title:"确认删除",content:"您确认要删除 "+t.name+" ?",loading:!0,onOk:()=>{(0,l.vD)({ids:t.id}).then((t=>{this.$Modal.remove(),t.success&&(this.$Message.success("操作成功"),this.getDataList())}))}})},delAll(){this.selectCount<=0?this.$Message.warning("您还未选择要删除的数据"):this.$Modal.confirm({title:"确认删除",content:"您确认要删除所选的 "+this.selectCount+" 条数据?",loading:!0,onOk:()=>{let t="";this.selectList.forEach((function(e){t+=e.id+","})),t=t.substring(0,t.length-1),(0,l.vD)({ids:t}).then((t=>{this.$Modal.remove(),t.success&&(this.$Message.success("操作成功"),this.clearSelectAll(),this.getDataList())}))}})}},mounted(){this.init(),this.mycolumns=this.columns;let t=[];for(var e=0;e<this.selected.length;e++)for(var s=this.selected[e],a=0;a<this.columns.length;a++)this.columns[a].title==s&&t.push(this.columns[a]);this.columns=t},watch:{selected:function(t){let e=[];for(var s=0;s<this.mycolumns.length;s++){var a=this.mycolumns[s];(void 0==a.title||t.includes(a.title))&&e.push(a)}this.columns=e}}},c=n;var m=s(1001),d=(0,m.Z)(c,a,i,!1,null,null,null);const h=d.exports},7722:(t,e,s)=>{s.d(e,{N:()=>l,QX:()=>o,lI:()=>i,vD:()=>r});var a=s(8571);const i=t=>(0,a.A_)("/adminAssetUnit/getByPage",t),l=t=>(0,a.j0)("/adminAssetUnit/insert",t),o=t=>(0,a.j0)("/adminAssetUnit/update",t),r=t=>(0,a.j0)("/adminAssetUnit/delByIds",t)}}]);