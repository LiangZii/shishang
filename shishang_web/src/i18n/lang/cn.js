/**
 * Created by WebStorm.
 * User: nirongxu
 * Date: 2018/12/8
 * Description: 文件描述
 */
import zhLocale from "element-ui/lib/locale/lang/zh-CN"
const cn = {
  routeName: {
    home: "主页",
    article: "网站后台管理",
    publishArticle: "网站后台管理",
    publishArticleEditor: "发表文章-富文本",
    icon: "图标",
    builtInIcon: "内置图标",

    permissions: "用户管理",
    pageControl: "用户信息管理",
    shuttleBox: "菜谱管理",
    demoShuttle: "菜谱信息查询",
    btnControl: "菜谱信息添加",
    table: "系统设置",
    multiDataTable: "用户信息管理",
    filterTable: "用户权限管理",
    table3:"添加用户",
    table4:"添加机构",
    dragSort: "拖拽排序",
    upload: "备件管理",
    fileUpload: "备件基础资料管理",
    editor: "综合查询",
    markdown: "故障设备查询",
    wangeditor: "机组查询",
    search3:"设备状态查询",
    search4:"汇总与分析",
    multiDirectory: "检修管理",
    "menu2-1": "检修信息",
    "menu2-2": "新增检修",
    "menu2-3": "二级-3",
    "menu3-1": "三级-1",
    "menu3-2": "三级-2",
    "menu3-3": "三级-3",
    "menu4-1": "四级-1",
    "menu4-2": "四级-2",
    "menu5-1": "五级-1",
    systemSettings: "检修管理",
    navMenu: "预防性检修信息"

  },
  rightMenu: {
    close: "关闭",
    closeOther: "关闭其他",
    closeAll: "全部关闭"
  },
  role: {
    superAdmin: "超级管理员",
    admin: "管理员",
    ordinary: "普通用户"
  },
  userDropdownMenu: {
    basicInfor: "基本资料",
    changePassword: "修改密码",
    logout: "退出"
  },

  ...zhLocale //  合并element-ui内置翻译
}

export default cn
