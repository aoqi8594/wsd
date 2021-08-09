<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <Layergroup3 ref="Layergroup3" @returnvalue="returnvalue"></Layergroup3>
        <div class="grid-content bg-purple">
          <Roomcar ref="Roomcar"  @callBackroo="callBackroo"></Roomcar>
          <el-scrollbar ref="myScrollbar" style="height: 70vh">
           <Freezer ref="roomlist" @callBackree="callBackree" />
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-light">
          <!-- <div class="light-up">
            <p>各时间段温湿度统计</p>
            <Brokenline ref="detection"></Brokenline>
          </div> -->
          <Eltable2 ref="Eltable2"></Eltable2>
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>
<script>
import Layergroup3 from "@/components/Layergroup3.vue";
import Freezer from "@/components/Freezer";
import Brokenline from "@/components/Brokenline.vue";
import Roomcar from "@/components/Roomcar.vue";
import Eltable2 from "@/components/Eltable2.vue";
export default {
  components: {
    Layergroup3,
    Freezer,
    Brokenline,
    Roomcar,
    Eltable2
  },
  data() {
    return {
      // roomlist: [1, 2, 3, 4, 5, 6, 7, 8,9,10,11,11,2,34,4],
      // lists: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
      form:{
        region: '',
      },
      carid:'',//当前车的ID 
      arraylist:'',//获取到的所有数据温度计
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
    };
  },
  mounted() {
    if(this.$route.params.id){
       this.thermometer(this.$route.params.id,this.$route.params.parent);
      //  this.getLayerdata(this.$route.params.id)
    }else{
      this.thermometer(JSON.parse(localStorage.getItem("cardata")).id,'cardetails');
      //  this.getLayerdata(JSON.parse(localStorage.getItem("cardata")).id)
    }
    // console.log(this.$route.params.id)
    // this.thermometer(this.$route.params.id,this.$route.params.parent);
  },
  methods: {
    getHistoryList(dev_no) {
      this.$api.historical.getHistoryList({
          belong_type:'',
          use_type:'',
          dev_no:dev_no,
          startTime:this.$date.getDate3(),
          endTime:this.$date.getDate3(), 
          alarm_yn:'',
          dev_name:'',
          page: this.pageIndex, 
          limit: this.pageLimt 
        }).then(data => {
        // this.totalPage = data.data.paging.total;
        if (data.code == 0) {
          console.log("ggg",data)
          let array = data.data.list;
           this.$refs.Eltable2.init(array);
         
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    getLayerdata(id) {
      this.ftag=true;
      this.$api.refrigerator
        .getrefrigeratorlist({ carroom_id: id, type: "LLC" })
        .then(data => {
          if (data.code == 0) {
            // this.totaldata = data.data;
            let array = data.data.list;
            let devlist = [];
            for (let i = 0; i < array.length; i++) {
              devlist = devlist.concat(array[i].devList);
            }
            this.$refs.Eltable2.init(devlist);
          } else {
            this.$message.error(data.msg);
          }
        }); 
    },
    //查询获取车辆列表
    thermometer(id,parent) {
      this.carid=id;
      this.$api.vehicleManagement.getvehicleList({driver_name:'',car_no:'', page: 1,limit: 10000,}).then(data => {
        if (data.code == 0) {
          this.arraylist=data.data.list;
          let array = data.data.list;
          this.$refs.Layergroup3.init(array,id,parent)
          for (let i = 0; i < array.length; i++) {
            if(array[i].id==id){
              let roomInfo={car_no:array[i].car_no}
              this.$refs.Roomcar.init(roomInfo,array[i],'car');
              let boxlist=[];
              for (let j = 0; j < array[i].iceboxList.length; j++) {
                for (let k = 0; k < array[i].iceboxList[j].devList.length; k++) {
                 boxlist.push(array[i].iceboxList[j].devList[k])
                }
              }
              this.$refs.roomlist.init(boxlist, 0,'car');
              if(boxlist.length>0){
               this.getHistoryList(boxlist[0].dev_no)
              }
            }
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    callBackroo(e) {
        for (let i = 0; i < this.arraylist.length; i++) {
            if(this.arraylist[i].id==this.carid){
              for (let j = 0; j < this.arraylist[i].iceboxList.length; j++) {
                let boxlist=[];
                if(this.arraylist[i].iceboxList[j].id==e){
                  for (let k = 0; k < this.arraylist[i].iceboxList[j].devList.length; k++) {
                    boxlist.push(this.arraylist[i].iceboxList[j].devList[k])
                  }
                }
                 this.$refs.roomlist.init(boxlist, 0);
              }
            }
          }
    },
    returnvalue(e,parent){
      this.thermometer(e,parent)
    },
    callBackree(e,id,dev_no) {
      console.log("hhh",e)
      console.log("hhhid",id)
      console.log("dev_no",dev_no)
       this.getHistoryList(dev_no)
      //  this.$refs.detection.init(id);
      // this.num = e;
      // this.dev = {
      //   dev_name: this.devlist[e].dev_name,
      //   duty_name: this.devlist[e].duty_name,
      //   duty_cont_phon: this.devlist[e].duty_cont_phon
      // };
    },
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
