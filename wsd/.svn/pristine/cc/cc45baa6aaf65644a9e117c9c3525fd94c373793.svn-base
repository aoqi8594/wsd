<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <Layergroup2 @callBackLayer="callBackLayer" ref="layergroup"></Layergroup2>
        <div class="grid-content bg-purple" v-show="ftag">
          <Roomcar @callBackroo="callBackroo" ref="roomcar"></Roomcar>
          <el-scrollbar ref="myScrollbar" style="height: 70vh">
            <Freezer @callBackree="callBackree" ref="roomlist"/>
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-light">
          <div class="light-top">
            <div class="instrument">{{dev.dev_name}}</div>
            <div class="personnel">
              <span class="position">负责人:{{dev.duty_name}}</span>
              <span class="phone"> 
                <i class="iconfont icon-shouji"></i>
                {{dev.duty_cont_phon}}
              </span>
            </div>
            <div class="roomaddr">
              <i class="iconfont icon-dizhi"></i>
              <span class>{{seat}}</span> 
            </div>
          </div>
          <div class="light-up">
            <p>各时间段温湿度统计</p>
            <Brokenline ref="detection" height="height:50vh;"></Brokenline>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Layergroup2 from "@/components/Layergroup2.vue";
import Freezer from "@/components/Freezer";
import Brokenline from "@/components/Brokenline.vue";
import Roomcar from "@/components/Roomcar.vue";
export default {
  components: {
    Layergroup2,
    Freezer,
    Brokenline,
    Roomcar
  },
  data() {
    return {
      devlist: [],
      dev: "",
      num: 0,
      seat: "",
      totaldata: "",
      ftag:true,
    };
  },
  mounted() {
    let floordata = JSON.parse(localStorage.getItem("floordata"));
    this.getLayerdata(floordata.id,1,floordata.room_cd);
  },
  methods: {
    getLayerdata(id,tag,room_cd) {
      this.ftag=true;
      this.$api.refrigerator
        .getrefrigeratorlist({ carroom_id: id, type: "FJ" })
        .then(data => {
          if (data.code == 0) {
            this.totaldata = data.data;
            let roomInfo = data.data.roomInfo[0];
            let array = data.data.list;
            if(room_cd){
               this.$refs.layergroup.getbuilding(
                roomInfo.build_id,
                roomInfo.build_name,
                roomInfo.floor_cd,
                room_cd,
                tag
              );
            }else{
              this.$refs.layergroup.getbuilding(
                roomInfo.build_id,
                roomInfo.build_name,
                roomInfo.floor_cd,
                roomInfo.room_cd,
                tag
              );
            }
            this.$refs.roomcar.init(roomInfo, array,'room');
            // this.$refs.roomlist.init(array);
            this.seat =roomInfo.build_name + roomInfo.floor_cd + "楼" + roomInfo.room_cd;

            let devlist = [];
            for (let i = 0; i < array.length; i++) {
              devlist = devlist.concat(array[i].devList);
            }
            this.devlist = devlist;
            if(devlist.length>0){
               this.dev = {
                dev_name: devlist[this.num].dev_name,
                duty_name: devlist[this.num].duty_name,
                duty_cont_phon: devlist[this.num].duty_cont_phon
                
              };
              this.$refs.detection.init(devlist[0].id);
            }else{
               this.dev = {
                dev_name: '暂位绑定温度计',
                duty_name: '',
                duty_cont_phon: ''
               }
            }
            this.$refs.roomlist.init(devlist, 0,'room');
            console.log(devlist)
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    callBackree(e,id) {
       this.$refs.detection.init(id);
      this.num = e;
      this.dev = {
        dev_name: this.devlist[e].dev_name,
        duty_name: this.devlist[e].duty_name,
        duty_cont_phon: this.devlist[e].duty_cont_phon
      };
    },
    callBackroo(e) {
      this.num = 0;
      if (e == "") {
        let devlist = [];
        let array = this.totaldata.list;
        for (let i = 0; i < array.length; i++) {
          devlist = devlist.concat(array[i].devList);
        }
        this.devlist = devlist;
        this.dev = {
          dev_name: devlist[this.num].dev_name,
          duty_name: devlist[this.num].duty_name,
          duty_cont_phon: devlist[this.num].duty_cont_phon
        };
        this.$refs.roomlist.init(devlist, 0,'room');
        this.$refs.detection.init(devList[0].id);
      } else {
        for (let i = 0; i < this.totaldata.list.length; i++) {
          if (this.totaldata.list[i].id == e) {
            this.$refs.roomlist.init(this.totaldata.list[i].devList, 0);
            this.$refs.detection.init(this.totaldata.list[i].devList[0].id);
            this.devlist = this.totaldata.list[i].devList;
            this.dev = {
              dev_name: this.devlist[0].dev_name,
              duty_name: this.devlist[0].duty_name,
              duty_cont_phon: this.devlist[0].duty_cont_phon
            };
          }
        }
      }
    },
    callBackLayer(e) {
      this.num = 0;
      if(e){
          this.getLayerdata(e.id,2,'');
      }else{
         this.ftag=false;
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.home {
  height: calc(100vh - 50px);
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
    }
  }
  .bg-purple {
    height: 100%;
    background: #f5f7fa;
    padding: 0.3rem;
  }
  .bg-purple-light {
    height: 100%;
    background: #fff;
    padding: 0.16rem;
    .light-top {
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      padding: 0.2rem;
      .instrument {
        font-size: 0.18rem;
        padding-right: 0.1rem;
        color: #1a1a1a;
        margin-bottom: 0.1rem;
      }
      .roomaddr {
        width: 50%;
        margin-left: 25%;
        height: 0.4rem;
        line-height: 0.4rem;
        text-align: center;
        border-radius: 0.06rem;
        background-color: #f5f5f5;
        color: #1a90fd;
        .icon-dizhi {
          color: #cccccc;
          font-size: 0.18rem;
        }
      }
      .personnel {
        font-size: 0.12rem;
        margin-bottom: 0.4rem;
        color: #999999;
        .phone {
          float: right;
        }
      }
    }
    .light-up {
      margin-top: 0.14rem;
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      padding: 0.2rem 0;
      height: calc(100vh - 2.7rem);
      p {
        font-size: 0.14rem;
        font-weight: bold;
        color: #1a1a1a;
        padding: 0 0.2rem 0.1rem;
      }
    }
  }
}
</style>
