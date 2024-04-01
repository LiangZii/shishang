import en from "../i18n/lang/en"
import Vue from "vue"
import Router from "vue-router"
import CommerViews from "@/views/commerViews"
import Login from "@/views/login/index"
import Layout from "@/views/layout/layout"
import HomeMain from "@/views/index/mainIndex"

// 不是必须加载的组件使用懒加载
const Icon = () => import("@/views/icon/index")
const Erji = () => import("@/views/duoji/erji")
const Erji2 = () => import("@/views/duoji/erji2")
const Sanji = () => import("@/views/duoji/sanji")
const Sanji2 = () => import("@/views/duoji/sanji2")
const Siji = () => import("@/views/duoji/siji")
const Wuji = () => import("@/views/duoji/wuji")
const Transfer = () => import("@/views/transfer/transfer")
const DataTable = () => import("@/views/table/dataTables")
const FilterTable = () => import("@/views/table/filterTable")
const Table3 = () => import("@/views/table/table3")
const Table4 = () => import("@/views/table/table4")
const DragTable = () => import("@/views/table/dragTabe")
const Upload = () => import("@/views/upload/upload")
const Markdown = () => import("@/views/editor/markdownView")
const WangeditorView = () => import("@/views/editor/wangeditorView")
const search3 = () => import("@/views/editor/search3")
const search4 = () => import("@/views/editor/search4")
const NotFound = () => import("@/views/page404")
const AddArticle = () => import("@/views/article/addArticle")
const AddArticleEditor = () => import("@/views/article/addArticleEditor")
const NavClassify = () => import("@/views/syssetting/navClassify")
const pagePermissions = () => import("@/views/permissions/pagePermissions")
const btnPermissions = () => import("@/views/permissions/btnPermissions")

/**
 * 重写路由的push方法
 */
const routerPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(error => error)
}
Vue.use(Router)
let routeName = en.routeName
let defaultRouter = [
  { path: "/",
    redirect: "/index",
    hidden: true,
    children: []
  },
  {
    path: "/login",
    component: Login,
    name: "",
    hidden: true,
    children: []
  },
  {
    path: "/index",
    iconCls: "fa fa-bank", // 图标样式class
    name: routeName.home,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/index",
        iconCls: "fa fa-bank", // 图标样式class
        name: "主页",
        component: HomeMain,
        children: []
      }
    ]
  },
  {
    path: "/404",
    component: NotFound,
    name: "404",
    hidden: true,
    children: []
  }
]

let addRouter = [
  {
    path: "/",
    iconCls: "el-icon-tickets", // 图标样式class
    name: routeName.article,
    component: Layout,
    children: [
      {
        path: "/crew",
        iconCls: "el-icon-edit-outline", // 图标样式class
        name: routeName.publishArticle,
        component: AddArticle,
        children: []
      }
    ]
  },
  {
    path: "/",
    iconCls: "fa fa-wrench", // 图标样式class
    name: routeName.permissions,
    component: Layout,
    children: [
      {
        path: "/repair",
        iconCls: "el-icon-edit-outline", // 图标样式class
        name: routeName.pageControl,
        component: pagePermissions,
        children: []
      },
    ]
  },
  {
    path: "/",
    iconCls: "fa fa-sort-amount-asc", // 图标样式class
    name: routeName.shuttleBox,
    component: Layout,
    children: [
      {
        path: "/fault",
        iconCls: "el-icon-edit-outline", // 图标样式class
        name: routeName.demoShuttle,
        component: Transfer,
        children: []
      },
      {
        path: "/faultimage",
        iconCls: "el-icon-edit-outline", // 图标样式class
        name: routeName.btnControl,
        component: btnPermissions,
        children: []
      }
    ]
  },
  { path: "*",
    redirect: "/404",
    hidden: true,
    children: []
  }

]
export default new Router({
  routes: defaultRouter
})
export {defaultRouter, addRouter}
