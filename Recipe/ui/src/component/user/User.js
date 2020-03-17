import React from 'react'
import { Route } from 'react-router-dom';

import './User.css'
import LoginForm from './login/Login.Form';
import RegisterForm from './register/Register.Form';
import logoURL from '../../assets/img/logo.jpg';

import { Layout } from 'antd';
const { Content, Footer, Header } = Layout;

export class User extends React.Component {

    state = {
        url: 'https://shop316822115.taobao.com/?spm=a230r.7195193.1997079397.2.10b84a18miiyT3'
    }

    render() {
        return (
            <Layout style={{ minHeight: '100vh' }}>
                <Header style={{ background: '#000', padding: 0 }}>
                    <div className="container">
                        <div className="logo" >
                            <img alt="心筑月" src={logoURL} onClick={() => this.open()}/>
                        </div>
                    </div> 
                </Header>
                 <Content style={{ margin: '0 16px' }}>
                    <div className="main">
                        <Route component={LoginForm} path='/user/login' exact></Route>
                        <Route component={RegisterForm} path='/user/register' exact></Route>
                        <Route component={LoginForm} path='/' exact></Route>
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    <img  style={{ width: '20px'  }} src="http://img.alicdn.com/tfs/TB1..50QpXXXXX7XpXXXXXXXXXX-40-40.png"/>
                    <a href="http://beian.miit.gov.cn/"> 沪ICP备20005925号</a>
                    <span> © 2020-2022 shibonas.cn 版权所有</span>
                </Footer>
            </Layout>
        );
    }

    open() {
        window.open(this.state.url);
    }
}