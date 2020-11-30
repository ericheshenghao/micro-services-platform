<template>
    <div class="bg-white h-100">
        <el-container class="pt-3 px-3">
            <el-main>
                <div class="px-3 py-2">
                    <el-breadcrumb class="py-2" separator-class="el-icon-arrow-right">
                        <el-breadcrumb-item></el-breadcrumb-item>
                    </el-breadcrumb>

                    <avue-crud
                            :page.sync="page"
                            :table-loading="loading"
                            @on-load="onLoad"
                            @row-update="rowUpdate"
                            @row-save="rowSave"
                            @row-del="rowDel"
                            :permission="permission"
                            :data="tableData"
                            :option="option"
                    >
                    </avue-crud>
                </div>
            </el-main>
        </el-container>
    </div>
</template>
<script lang='ts'>
    import { Vue, Component } from 'nuxt-property-decorator'
    @Component({
        components: {},
    })
    export default class index extends Vue {
        loading = true
        tableData = []
        form = {}
        http = Vue.prototype.$http
                page: any = {
            total: 10,
            pagerCount: 5,
            currentPage: 1,
            pageSize: 10,
            pageSizes: [5, 10, 20],
            layout: 'total, sizes,prev, pager, next, jumper',
            background: false,
        }


        async checkAuth() {
            this.permission.delBtn = await this.$store.dispatch(
              'checkAuth',
               'sys:',${pathName},':edit'
             )
            this.permission.addBtn = await this.$store.dispatch(
                'checkAuth',
                'sys:',${pathName},':add'
            )
                    this.permission.menu = await this.$store.dispatch(
                'checkAuth',
                'sys:',${pathName},':edit'
            )
        }

        permission = {
            delBtn: true,
            addBtn: true,
            menu: true,
        }

        mounted() {
            this.checkAuth()
        }

        option = {
            height: '450',
            // sortable: true,
            // selection: true,
            menuWidth: 210,
            index: true,
            size: 'mini',
            dialogDrag: true,
            column: [
            {
                label: '',
                prop: 'id'
            },
            {
                label: '',
                prop: 'accessTokenValiditySeconds'
            },
            {
                label: '',
                prop: 'authorizations'
            },
            {
                label: '',
                prop: 'autoApproveScopes'
            },
            {
                label: '',
                prop: 'clientId'
            },
            {
                label: '',
                prop: 'clientSecret'
            },
            {
                label: '',
                prop: 'grantTypes'
            },
            {
                label: '',
                prop: 'redirectUrl'
            },
            {
                label: '',
                prop: 'refreshTokenValiditySeconds'
            },
            {
                label: '',
                prop: 'resourceIds'
            },
            {
                label: '',
                prop: 'scopes'
            },
            ],
        }


        async rowSave(form: any, done: any, loading: any) {
            setTimeout(() => {
                done(form)
            }, 500)

            const res = await this.http.post('pri/sysClientDetails/save', form, { prefix: 'admin' })
        }


        async rowUpdate(form: any, index: any, done: any, loading: any) {
            setTimeout(() => {
                done(form)
                this.onLoad()
            }, 500)
            const res = await this.http.put('pri/sysClientDetails/'+form.id, form, { prefix: 'admin' })
        }


        async rowDel(form: any,index: any){
            await this.http.delete('pri/sysClientDetails/'+form.id, { prefix: 'admin' })
        }

        async onLoad() {
            this.loading = true

            const { data } = await this.http.post(
                    'pri/sysClientDetails/findPage',
                    {
                        pageNum: this.page.currentPage,
                        pageSize: this.page.pageSize,
                        params: {},
                    },
                    { prefix: 'admin' }
            )
            this.page.total = data.data.totalSize

            setTimeout(() => {
                this.loading = false
                this.tableData = data.data.content
            }, 500)

        }
    }
</script>
<style lang='scss' scoped>
</style>