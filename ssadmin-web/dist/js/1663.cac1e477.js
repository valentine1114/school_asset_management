"use strict";(self["webpackChunktemplate"]=self["webpackChunktemplate"]||[]).push([[1663],{1663:(e,t,l)=>{l.r(t),l.d(t,{default:()=>u});var a=function(){var e=this,t=e._self._c;return t("div",[t("Cascader",{attrs:{data:e.department,"load-data":e.loadData,"change-on-select":"",filterable:"",clearable:"",placeholder:"请选择或输入搜索部门"},on:{"on-change":e.handleChangeDep},model:{value:e.selectDep,callback:function(t){e.selectDep=t},expression:"selectDep"}})],1)},n=[],s=l(5102);const i={name:"departmentChoose",props:{},data(){return{selectDep:[],department:[]}},methods:{initDepartmentData(){(0,s.E5)().then((e=>{e.success&&(e.result.forEach((function(e){e.isParent?(e.value=e.id,e.label=e.title,e.loading=!1,e.children=[]):(e.value=e.id,e.label=e.title),-1==e.status&&(e.label="[已禁用] "+e.label,e.disabled=!0)})),this.department=e.result)}))},loadData(e,t){e.loading=!0,(0,s.G8)(e.value).then((l=>{e.loading=!1,l.success&&(l.result.forEach((function(e){e.isParent?(e.value=e.id,e.label=e.title,e.loading=!1,e.children=[]):(e.value=e.id,e.label=e.title),-1==e.status&&(e.label="[已禁用] "+e.label,e.disabled=!0)})),e.children=l.result,t())}))},handleChangeDep(e,t){let l="";e&&e.length>0&&(l=e[e.length-1]),this.$emit("on-change",l)},clearSelect(){this.selectDep=[]}},mounted(){this.initDepartmentData()}},c=i;var d=l(1001),r=(0,d.Z)(c,a,n,!1,null,null,null);const u=r.exports}}]);