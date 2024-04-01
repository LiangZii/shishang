import Mock from 'mockjs'

Mock.mock('http://localhost:8080/mainindex',{
    "ret":0,
    "text":"123",
    "data":
    {
        "data1":
        [{
            deviceid:"01",
            devicename:"西红柿炒鸡蛋",
            faultdate:"65530",
            fieldname:"食尚小厨111"
        },{
            deviceid:"02",
            devicename:"烤鸭",
            faultdate:"54467",
            fieldname:"食尚小厨222"
        },{
            deviceid:"03",
            devicename:"糖醋排骨",
            faultdate:"43698",
            fieldname:"食尚小厨333"
        }],
        "data3":
        [{
            deviceid:"01",
            devicename:"提升系统安全性，防止用户数据泄露",
            time:"2023-05-07",
            fieldname:"一级"
        },{
            deviceid:"02",
            devicename:"更换服务器设备，提升系统响应速度",
            time:"2023-05-20",
            fieldname:"二级"
        }]
    }
})

Mock.mock('http://localhost:8080/login',{
    "ret":0,
    "text":"123",
    "data":
    {
        "password":"123456",
        "role":"02"//role返回“01”是超级管理员，返回“02”是普通用户
    }
})

Mock.mock('http://localhost:8080/transfer',{
    "ret":0,
    "data":
    [{
        "id":"001",
        "picture":'https://a1.cdn.91360.com/cms/bs3/upload/section/31c9f4a94769e924b7ccd764c075b29a_t.png',
        "name":"西红柿炒鸡蛋"
    }]
})

Mock.mock('http://localhost:8080/xxx',{
    "ret":0,
    "text":"123",
    "data":
    {
        "mtime":"@datatime",//随机生成日期时间
        "data":[1,5000,1,5000,1,5000,1]
    }
})

var datayyy=[{
            crewid:"01",
            crewbh:"001",
            managerid:"001",
            fieldname:"宏伟大风场",
            manufacturer:"宏伟风机有限公司",
            model:"50kg",
            installdate:"2016-05-02",
            agelimit:"5",
            status:"良好",
            address: "澳大利亚草原",
            crewpower:"123Ah",
            sumpower:"456Ah"
        }]

const insertcrew = function (req) {
    const yyy = JSON.parse(req.body)
    console.log(yyy)
    datayyy.push(yyy)
    console.log(datayyy)
}

Mock.mock('http://localhost:8080/addarticle',{
    "data":datayyy
    })

Mock.mock('http://localhost:8080/addarticle/insertcrew',insertcrew)
Mock.mock('http://localhost:8080/addarticle/updatecrew',insertcrew)


Mock.mock('http://localhost:8080/pagepermission',{
    "data":[{
        userId:"001",
        name:"食尚小厨01",
        password:"******",
        head:"18812789921",
        register:"108",
        goodnum:"99",
        sendnum:"73",
      },
      {
        userId:"002",
        name:"食尚小厨01",
        password:"******",
        head:"18812789921",
        register:"108",
        goodnum:"99",
        sendnum:"73",
      }]
})

Mock.mock('http://localhost:8080/upload',{
    "data": [{
        partid:"001",
        name:"备份一号",
        model:"50kg",
        quantity:"100",
        unit:"台",
        price:"100元",
        indate:"2022-01-01",
        outdate:"2022-01-02",
        useitem:"使用",
        sapid:"01",
        stationid:"01",
        manufactorid:"01",
        type:"新件",
      }]
    })

Mock.mock('http://localhost:8080/datatable',{
    "data":[{
        name:"麒麟",
        password:"123456",
        id:"01",
        role:"设备管理员",
        jurisdiction:"维修损坏比较严重的机器",
      }]
})



Mock.mock('http://localhost:8080/search1',{
    "data":[{
        faultid:"001",
        faultname:"雷劈1",
        comment:"下雨天被雷劈到了",
        handletime:"2022-01-01",
        resetime:"2022-01-03",
        lostpower:"500kWh"
      }]
})

Mock.mock('http://localhost:8080/search2',{
    "data":[{
        crewid:"01",
        crewbh:"001",
        fieldname:"宏伟机组一号",
        crewpower:"123kWh",
        sumpower:"456kWh"
    }]
})

Mock.mock('http://localhost:8080/search3',{
    "data":[{
        crewid:"01",
        crewbh:"001",
        status:"良好",
        cost:"1000元",
        quantity:"消耗20个备件"
    }]
})

Mock.mock('http://localhost:8080/search4',{
    "data":{
        xx1:[1,2,3,4,5],
        xx2:[1,2,3,4,5],
        xx3:[1,2,3,4,5],
        xx4:[1,2,3,4,5],
        xx5:[1,2,3,4,5],
}
})

Mock.mock('http://localhost:8080/pie/init',{
    "data":{
        trust1:0.2,
        trust2:0.2,
        trust3:0.6,
        comment:"请及时检修各设备，并且及时维修故障设备"
}
})

Mock.mock('http://localhost:8080/pie/receive',{
    "data":{
        trust1:0.1,
        trust2:0.1,
        trust3:0.8,
        comment:"麒麟小王子xx"
}
})

var dataxxx=[{
        haulid:"001",
        mission:"对所有设备进行检查",
        people:"麒麟",
        content:"设备整体检查",
        regular:"半年检",
        time:"2022-01-01",
        comment:"无"
  }]
  

const insert = function (req) {
    const yyy = JSON.parse(req.body)
    console.log(yyy)
    dataxxx.push(yyy)
    console.log(dataxxx)
}

Mock.mock('http://localhost:8080/erji',{
    "data":dataxxx
})

Mock.mock('http://localhost:8080/erji2',insert)


Mock.mock('http://localhost:8080/diet/read',{
    
        "code": 200,
        "msg": "操作成功",
        "data": [
            {
                "id": 0,
                "name": "茄汁浓郁超下饭的番茄娃娃菜❗️",
                "foods": [
                    {
                        "id": 0,
                        "name": "1颗娃娃菜",
                        "energy": 0
                    },
                    {
                        "id": 0,
                        "name": "适量大蒜",
                        "energy": 0
                    },
                    {
                        "id": 0,
                        "name": "2个番茄",
                        "energy": 0
                    }
                ],
                "energies": 0,
                "message": "综合评分 7.9 （24 做过）",
                "method": [
                    {
                        "message": "1⃣️番茄切成块，娃娃菜切成片",
                        "src": "https://i2.chuimg.com/1446f3c676784ffeb1373af5ca253a96_1080w_1439h.jpg?imageView2/2/w/300/interlace/1/q/90"
                    },
                    {
                        "message": "2⃣️锅中倒油，放蒜末爆香，下番茄块炒软出汁；接着放娃娃菜，超软后加1勺生抽、1茶匙盐翻炒均匀，直到炒熟！",
                        "src": "https://i2.chuimg.com/da7dbde60e4e4dd7990f7b6b58253de6_1080w_1439h.jpg?imageView2/2/w/300/interlace/1/q/90"
                    }
                ],
                "img": "https://i2.chuimg.com/2793387e20244a9994c354ec2e4f7807_2073w_2560h.jpg?imageView2/1/w/215/h/136/interlace/1/q/90",
                "src": "106881562"
            }
        ]
    
})
