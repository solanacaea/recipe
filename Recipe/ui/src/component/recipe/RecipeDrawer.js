import {
    Drawer, Form, Button, Col, Row, Input, Select, DatePicker, Icon,
  } from 'antd';
import React, { Component } from 'react'; 
import DropdownCategory from './DropdownCategory'
import DropdownOptimalStage from './DropdownOptimalStage'
import DropdownOptimalTime from './DropdownOptimalTime'
import DropdownProperty from './DropdownProperty'
import DropdownEfficacy from './DropdownEfficacy'

  const { Option } = Select;
  
  class DrawerForm extends Component {
      
    componentDidMount() {
      this.props.onRef(this);
    }

    state = { visible: false, data: null};
  
    showDrawer = (param) => {
      this.setState({
        visible: true,
        data: param,
      });

      console.log(param);
      //this.props.form.setFieldsValue({ name:"张三", })
    };
  
    onClose = () => {
      this.setState({
        visible: false,
      });
    };

    onRefCategory = (ref) => {
      this.category = ref;
    }
    onRefOptimalStage = (ref) => {
      this.optimalStage = ref;
    }
    onRefOptimalTime = (ref) => {
      this.optimalTime = ref;
    }
    onRefProperty = (ref) => {
      this.property = ref;
    }
    onRefEfficacy = (ref) => {
      this.efficacy = ref;
    }

    onSubmit = () => {
      //console.log(this.props.form.getFieldsValue("名称"));
      console.log(this.state.name);
      console.log(this.category.getValue());
      console.log(this.optimalStage.getValue());
      console.log(this.optimalTime.getValue());
      console.log(this.property.getValue());
      console.log(this.efficacy.getValue());
    };

    render() {
      const { getFieldDecorator } = this.props.form;
      return (
        <div>
          <Drawer
            title="看我不爽吗? 要改掉我吗?"
            width={720}
            onClose={this.onClose}
            visible={this.state.visible}
          >
            <Form layout="vertical" hideRequiredMark>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="名称">
                    {getFieldDecorator('description', {
                      rules: [
                        {
                          required: true,
                          message: '请输入食材名称...',
                        },
                      ],
                    })(<Input rows={8} placeholder="请输入食材名称..." /> )}
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="类别">
                    <DropdownCategory onRef={this.onRefCategory} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="适宜阶段">
                    <DropdownOptimalStage onRef={this.onRefOptimalStage} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="适宜时间">
                    <DropdownOptimalTime onRef={this.onRefOptimalTime} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="属性">
                    <DropdownProperty onRef={this.onRefProperty} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="功效">
                    <DropdownEfficacy onRef={this.onRefEfficacy} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item label="食材">
                    {getFieldDecorator('content', {
                      rules: [
                        {
                          required: true,
                          message: '请输入食材内容...',
                        },
                      ],
                    })(<Input.TextArea rows={9} placeholder="请输入食材内容..." />)}
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
  
  const App = Form.create()(DrawerForm);

  export default App;