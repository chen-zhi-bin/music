<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="card-content">
                        <div class="card-left">
                            <el-icon>
                                <user />
                            </el-icon>
                        </div>
                        <div class="card-right">
                            <div class="card-num">{{ this.infoData.userCount }}</div>
                            <div>用户总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="card-content">
                        <div class="card-left">
                            <el-icon>
                                <headset />
                            </el-icon>
                        </div>
                        <div class="card-right">
                            <div class="card-num">{{ this.infoData.songCount }}</div>
                            <div>歌曲总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="card-content">
                        <div class="card-left">
                            <el-icon>
                                <mic />
                            </el-icon>
                        </div>
                        <div class="card-right">
                            <div class="card-num">{{ this.infoData.musicianCount }}</div>
                            <div>歌手数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="card-content">
                        <div class="card-left">
                            <el-icon>
                                <document />
                            </el-icon>
                        </div>
                        <div class="card-right">
                            <div class="card-num">{{ this.infoData.clickedTotal }}</div>
                            <div>点击总量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="canvas-pie">
                        <div id="sex-ratio-pie"></div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover" :body-style="{ padding: '0px' }">
                    <div class="canvas-pie">
                        <div id="seven-day-line-chart"></div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
  
<script>
import * as api from '../../api/api';
import * as echarts from "echarts";
export default {
    data() {
        return {
            infoData: {
                userCount: '',
                songCount: '',
                musicianCount: '',
                musician: {
                    woman: '',
                    man: '',
                    none: '',
                    combination: ''
                },
                clickedTotal: '',
                web_size_seven_day_before_view_count: '',
                web_size_six_day_before_view_count: '',
                web_size_five_day_before_view_count: '',
                web_size_four_day_before_view_count: '',
                web_size_three_day_before_view_count: '',
                web_size_two_day_before_view_count: '',
                web_size_one_day_before_view_count: ''
            }

        }

    },
    mounted() {
        this.getMusicCount();
        this.getUserCount();
        this.getMusicianCount();
        this.getTotal();
        this.getSevenHistory();
    },
    methods: {
        getMusicCount() {
            api.musicCount()
                .then(result => {
                    if (result.code === api.success_code) {
                        this.infoData.songCount = result.data.count;
                    }
                });
        },
        getUserCount() {
            api.userCount()
                .then(result => {
                    if (result.code === api.success_code) {
                        this.infoData.userCount = result.data.count;
                    }
                });
        },
        getMusicianCount() {
            api.musicianCount()
                .then(result => {
                    if (result.code === api.success_code) {
                        this.infoData.musicianCount = result.data.total;
                        this.infoData.musician.woman = result.data.woman;
                        this.infoData.musician.man = result.data.man;
                        this.infoData.musician.none = result.data.none;
                        this.infoData.musician.combination = result.data.combination;
                        this.changeBar();
                    }
                });
        },
        changeBar() {
            const myChart = echarts.init(document.getElementById("sex-ratio-pie"));
            var option;
            option = {
                title: {
                    text: '歌手比例',
                    left: 'center',
                    top: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '数量',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: true,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: false,
                                fontSize: 10,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            { value: this.infoData.musician.man, name: '男' },
                            { value: this.infoData.musician.woman, name: '女' },
                            { value: this.infoData.musician.combination, name: '组合' },
                            { value: this.infoData.musician.none, name: '未知' }
                        ]
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        getTotal() {
            api.mtotalCount()
                .then(result => {
                    if (result.code == api.success_code) {
                        this.infoData.clickedTotal = result.data.web_size_view_count;
                    }
                })
        },
        getSevenHistory() {
            api.sevenHistoryCount()
                .then(result => {
                    if (result.code == api.success_code) {
                        this.infoData.web_size_one_day_before_view_count = result.data.history[0].value;
                        this.infoData.web_size_two_day_before_view_count = result.data.history[1].value;
                        this.infoData.web_size_three_day_before_view_count = result.data.history[2].value;
                        this.infoData.web_size_four_day_before_view_count = result.data.history[3].value;
                        this.infoData.web_size_five_day_before_view_count = result.data.history[4].value;
                        this.infoData.web_size_six_day_before_view_count = result.data.history[5].value;
                        this.infoData.web_size_seven_day_before_view_count = result.data.history[6].value;
                        this.changeLineChart();
                    }
                })
        },
        changeLineChart() {
            var chartDom = document.getElementById('seven-day-line-chart');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                xAxis: {
                    type: 'category',
                    data: ['七天前', '六天前', '五天前', '四天前', '三天前', '两天前', '昨天']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [
                            this.infoData.web_size_seven_day_before_view_count,
                            this.infoData.web_size_six_day_before_view_count,
                            this.infoData.web_size_five_day_before_view_count,
                            this.infoData.web_size_four_day_before_view_count,
                            this.infoData.web_size_three_day_before_view_count,
                            this.infoData.web_size_two_day_before_view_count,
                            this.infoData.web_size_one_day_before_view_count
                        ],
                        type: 'line',
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {
                                fontSize: 20
                            }

                        }
                    }
                ]
            };

            option && myChart.setOption(option);
        }
    }
}

</script>
  
<style>
.el-row {
    margin-bottom: 20px;
}

.card-content {
    display: flex;
    align-items: center;
    justify-content: space-around;
    height: 100px;
    padding-left: 20%;
    text-align: center;
}

.card-left {
    display: flex;
    font-size: 3rem;
}

.card-right {
    flex: 1;
    font-size: 14px;
}

.card-num {
    font-size: 30px;
    font-weight: bold;
}

.canvas-pie {
    width: 100%;
    display: block;
}

#sex-ratio-pie {
    margin: 0 auto;
    /* 水平居中 */
    width: 254px;
    height: 298px;
    width: 100%;
    display: block;
}

#seven-day-line-chart {
    margin: 0 auto;
    width: 100%;
    display: block;
    height: 298px;
}
</style>
  