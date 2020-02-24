import React from 'react'
import { Button, Col, Form, Input, Row, Icon } from 'antd';

import './User.css'

const FormItem = Form.Item;

export class Captcha extends React.Component {

    state = {
        count: 0
    }

    render() {
        const { form } = this.props;
        if (!form) {
            return null;
        }

        const { getFieldDecorator } = form;
        const { count } = this.state;

        return (
            <FormItem>
                <Row gutter={8}>
                    <Col span={16}>
                        {getFieldDecorator('captcha', {
                                rules: [{ required: true, message: '请输入验证码！' }],
                            })(
                            <Input
                                prefix={<Icon type="captcha" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                type="captcha"
                                placeholder="验证码"
                            />
                        )}
                    </Col>
                    <Col span={8}>
                        <Button
                            disabled={!!count}
                            htmlType="submit"
                            size="large"
                            onClick={() => this.onGetCaptcha()}
                        >
                            {count ? `${count} 秒` : '获取验证码'}
                        </Button>
                    </Col>
                </Row>
            </FormItem>
        )
    }

    onGetCaptcha() {
        if (!this.props) {
            return;
        }

        const { form } = this.props;
        form.validateFields(['phone'], {}, (err, fieldsValue) => {
            if (err) {
                return;
            }

            const phoneValue = fieldsValue['phone'];
            const { getCaptcha } = this.props;
            const result = getCaptcha ? getCaptcha(phoneValue) : null;
            if (!result) {
                return;
            }

            if (result instanceof Promise) {
                result.then(this.runGetCaptchaCountDown);
            } else {
                this.runGetCaptchaCountDown();
            }
        });
    }

    runGetCaptchaCountDown = () => {
        let count = 59;
        this.setState({ count });
        this.interval = window.setInterval(() => {
          count -= 1;
          this.setState({ count });
          if (count === 0) {
              clearInterval(this.interval);
          }
        }, 1000);
    };
}