import React from 'react'
import {
    Form,
    Input,
    Button,
    Row,
    Col,
    Alert,
} from 'antd';
import {Link} from 'react-router-dom';

import { Captcha } from '../Captcha'
import * as UserService from '../../../service/UserService';
import '../User.css'

class RegisterForm extends React.Component {
    state = {
        submiting: false,
        error: null
    }

    render() {
        const { form } = this.props;
        const { getFieldDecorator } = form;

        return (
            <Form>
                <h3>注册</h3>
                {
                    this.rendererError()
                }
                <Form.Item>
                    {
                        getFieldDecorator('username', {
                            rules: [
                                {
                                    validator: (rule, value, callback) => {
                                        this.checkDuplicateName(value, callback);
                                    }
                                },
                                {
                                    required: true,
                                    message: '请输入用户名！',
                                }
                            ],
                        })(<Input placeholder="用户名" />,)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('email', {
                            rules: [
                                {
                                    type: 'email',
                                    message: '邮箱地址格式错误！',
                                },
                                {
                                    required: true,
                                    message: '请输入邮箱地址！',
                                }
                            ],
                        })(<Input placeholder="邮箱" />,)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('password', {
                            rules: [
                                {
                                    required: true,
                                    message: '请输入密码！',
                                },
                                {
                                    pattern: /^.{6,}$/,
                                    message: '至少输入六位密码！',
                                }
                            ]
                        })(<Input type="password" placeholder="至少6位密码，区分大小写" />)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('confirmPassword', {
                            rules: [
                                {
                                    required: true,
                                    message: '请输入密码！',
                                },
                                {
                                    validator: (rule, value, callback) => {
                                        this.checkConfirmPassword(value, callback);
                                    }
                                }
                            ]
                        })(<Input type="password" placeholder="确认密码" />)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('phone', {
                            rules: [
                                { required: true, message: '请输入手机号！' },
                                {
                                    pattern: /^1\d{10}$/,
                                    message: '手机号格式错误！',
                                }
                            ]
                        })(<Input placeholder="手机号" />)
                    }
                </Form.Item>
                <Captcha form={this.props.form} getCaptcha={this.getCaptcha} ></Captcha>
                <Form.Item>
                    <Row gutter={8}>
                        <Col span={12}>
                            <Button type="primary" htmlType="submit" className="submit" loading={this.state.submiting} onClick={(e) => this.onRegister(e)}>注册</Button>
                        </Col>
                        <Col span={8} style={{"float": "right"}}>
                            <Link to="./login">使用已有账户登录</Link>
                        </Col>
                    </Row>
                </Form.Item>
            </Form>
        );
    }

    checkDuplicateName(value, callback) {
        if (value.length == 0) {
            callback();
        } else if (value.length < 4) {
            callback(new Error("用户名长度要大于3个字符！"));
        } else {
            UserService.checkName(value).then(value => {
                console.log(value)
                if (value == true) {
                    callback(new Error('用户名已存在！'));
                } else {
                    callback();
                }
            }, err => {
                callback();
            });
        }
    }

    checkConfirmPassword(value, callback) {
        const { form } = this.props;
        if (!form) {
            return;
        }

        let password = form.getFieldValue('password');
        if (password && password !== value) {
          callback(new Error('两次输入的密码不匹配'));
        } else {
          callback();
        }
    }

    getCaptcha(phone) {
        return UserService.getCaptcha(phone).then();
    }

    onRegister(e) {
        e.preventDefault();
        const { form } = this.props;
        if (!form) {
            return;
        }

        form.validateFields(['username', 'email', 'password', 'phone', 'captcha'],(err, fieldsValue) => {
            if (err) {
                return;
            }

            this.setState({ submiting: true });
            UserService.register(fieldsValue).then(value => {
                const data = value.data;
                if (!data || data.status !== 'OK') {
                    this.setState({
                        error: value.data.body,
                        submiting: false
                    });
                } else {
                    this.setState({
                        submiting: false
                    });
                    window.location.pathname ='../recipe';
                }
            }, err => {
                this.setState({
                    error: '服务器发生错误，请联系管理员',
                    submiting: false
                });
                this.setState({ submiting: false});
            });
        });
        
    }
    rendererError() {
        const message = this.state.error;
        if (message) {
            return <Alert style={{ marginBottom: 24 }} message={message} type="error" showIcon />
        }
    }
}

export default Form.create({})(RegisterForm);