  import {
      Drawer, Form, Button, Col, Row, Input,
  } from 'antd';
  import axios from 'axios';
  import React, { Component } from 'react';

  import PropertyDropdown from './dropdown/PropertyDropdown';
  import constant from '../../core/constant/Constant'

  const { options } = constant;

  class DrawerForm extends Component {

    state = { visible: false, data: null};

    onClose = () => {
      this.props.close();
    };

    onSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          const { category, optimalStage, optimalTime, property, efficacy, name, content } = values;
          const { id } = this.props;
          axios.post('http://localhost:8080//dish/save', {
                id: id,
                name: name,
                content: content,
                category: category.toString(),
                optimalStage: optimalStage.toString(),
                optimalTime: optimalTime.toString(),
                property: property.toString(),
                efficacy: efficacy.toString(),
              }
            ).then((res)=>{
                this.props.refresh();
                this.onClose();
            }).catch((err)=>{
                console.log(err)
            });
        }
      });
    };

    render() {
      const { getFieldDecorator } = this.props.form;
      return (
        <div>
          <Drawer
            title="看我不爽吗? 要改掉我吗?"
            width={720}
            onClose={this.onClose}
            visible={this.props.visible}
          >
            <Form layout="vertical" hideRequiredMark>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="名称">
                    {
                      getFieldDecorator('name', {
                        rules: [
                          {
                            required: true,
                            message: '请输入食材名称...',
                          }
                        ]
                      })(<Input rows={8} placeholder="请输入食材名称..."/>)
                    }
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="类别">
                    {
                      getFieldDecorator('category')(<PropertyDropdown propertyName={'category'} plainOptions={options.categoryOptions}></PropertyDropdown>)
                    }
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="适宜阶段">
                    {
                      getFieldDecorator('optimalStage')(<PropertyDropdown propertyName={'optimalStage'} plainOptions={options.optimalStageOptions}></PropertyDropdown>)
                    }
                    
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="适宜时间">
                    {
                      getFieldDecorator('optimalTime')(<PropertyDropdown propertyName={'optimalTime'} plainOptions={options.optimalTimeOptions}></PropertyDropdown>)
                    }
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="属性">
                    {
                      getFieldDecorator('property')(<PropertyDropdown propertyName={'property'} plainOptions={options.propertyOptions}></PropertyDropdown>)
                    }
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="功效">
                    {
                      getFieldDecorator('efficacy')(<PropertyDropdown propertyName={'efficacy'} plainOptions={options.efficacyOptions}></PropertyDropdown>)
                    }
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item label="食材">
                    {
                      getFieldDecorator('content', {
                        rules: [
                          {
                            required: true,
                            message: '请输入食材内容...',
                          },
                        ],
                      })(<Input.TextArea rows={9} placeholder="请输入食材内容..."/>)
                    }
                  </Form.Item>
                </Col>
              </Row>
            </Form>
            <div
              style={{
                position: 'absolute',
                left: 0,
                bottom: 0,
                width: '100%',
                borderTop: '1px solid #e9e9e9',
                padding: '10px 16px',
                background: '#fff',
                textAlign: 'right',
              }}
            >
              <Button onClick={this.onClose} style={{ marginRight: 8 }}>
                Cancel
              </Button>
              <Button onClick={this.onSubmit} type="primary">
                Submit
              </Button>
            </div>
          </Drawer>
        </div>
      );
    }
  }

  export default Form.create()(DrawerForm);