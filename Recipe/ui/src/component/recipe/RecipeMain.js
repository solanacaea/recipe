import {
    Table, Input, Button, Icon, Divider, Popconfirm, message
  } from 'antd';
  import Highlighter from 'react-highlight-words';
  import React, { Component } from 'react';
  import RecipeDrawer from './RecipeDrawer'

  const confirmText = '确定要删除吗?';
  const data = [{
    key: '1',
    name: 'John Brown',
    category: '主食',
    optimalStage: '第一周', 
    optimalTime: '早餐', 
    property: '11', 
    efficacy: '祛湿', 
    content: 'New York No. 1 Lake Park',
  }, {
    key: '2',
    name: 'Joe Black',
    category: '菜',
    optimalStage: '第二周', 
    optimalTime: '早点', 
    property: '22', 
    efficacy: '清热', 
    content: 'London No. 1 Lake Park',
  }, {
    key: '3',
    name: 'Jim Green',
    category: '汤',
    optimalStage: '第三周', 
    optimalTime: '早餐, 中餐', 
    property: '33', 
    efficacy: '解毒', 
    content: 'Sidney No. 1 Lake Park',
  }, {
    key: '4',
    name: 'Jim Red',
    category: '主食',
    optimalStage: '第一周, 第四周', 
    optimalTime: '中餐, 晚餐', 
    property: '44', 
    efficacy: '万能', 
    content: 'London No. 2 Lake Park',
  }];
  
  class RecipeMain extends Component {
    state = {
      searchText: '',
      filteredInfo: null,
      sortedInfo: null,
      selectedRow: null,
    };
  
    getColumnSearchProps = (dataIndex) => ({
      filterDropdown: ({
        setSelectedKeys, selectedKeys, confirm, clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            ref={node => { this.searchInput = node; }}
            placeholder={`Search ${dataIndex}`}
            value={selectedKeys[0]}
            onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
            style={{ width: 188, marginBottom: 8, display: 'block' }}
          />
          <Button
            type="primary"
            onClick={() => this.handleSearch(selectedKeys, confirm)}
            icon="search"
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Search
          </Button>
          <Button
            onClick={() => this.handleReset(clearFilters)}
            size="small"
            style={{ width: 90 }}
          >
            Reset
          </Button>
        </div>
      ),
      filterIcon: filtered => <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />,
      onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: (visible) => {
        if (visible) {
          setTimeout(() => this.searchInput.select());
        }
      },
      render: (text) => (
        <Highlighter
          highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
          searchWords={[this.state.searchText]}
          autoEscape
          textToHighlight={text.toString()}
        />
      ),
    })
  
    handleSearch = (selectedKeys, confirm) => {
      confirm();
      this.setState({ searchText: selectedKeys[0] });
    }
  
    handleReset = (clearFilters) => {
      clearFilters();
      this.setState({ searchText: '' });
    }

    handleChange = (pagination, filters, sorter) => {
      console.log('Various parameters', pagination, filters, sorter);
      this.setState({
        filteredInfo: filters,
        sortedInfo: sorter,
      });
    }

    clearFilters = () => {
      this.setState({ filteredInfo: null });
    }
  
    clearAll = () => {
      this.setState({
        filteredInfo: null,
        sortedInfo: null,
        selectedRow: null,
      });
    }

    render() {
      let { sortedInfo, filteredInfo } = this.state;
      sortedInfo = sortedInfo || {};
      filteredInfo = filteredInfo || {};

      const columns = [{
        title: '食谱名',
        dataIndex: 'name',
        key: 'name',
        ...this.getColumnSearchProps('name'),
      }, {
        title: '食谱',
        dataIndex: 'content',
        key: 'content',
        ...this.getColumnSearchProps('content'),
      }, {
        title: '类别',
        dataIndex: 'category',
        key: 'category',
        filters: [
          { text: '主食', value: '主食' },
          { text: '汤', value: '汤' },
          { text: '菜', value: '菜' },
          { text: '饮品', value: '饮品' },
        ],
        filteredValue: filteredInfo.category || null,
        onFilter: (value, record) => record.category.includes(value),
        sorter: (a, b) => a.category.length - b.category.length,
        sortOrder: sortedInfo.columnKey === 'category' && sortedInfo.order,
      }, {
        title: '适宜阶段',
        dataIndex: 'optimalStage',
        key: 'optimalStage',
        filters: [
          { text: '第一周', value: '第一周' },
          { text: '第二周', value: '第二周' },
          { text: '第三周', value: '第三周' },
          { text: '第四周', value: '第四周' },
          { text: '第五周', value: '第五周' },
          { text: '第六周', value: '第六周' },
        ],
        filteredValue: filteredInfo.optimalStage || null,
        onFilter: (value, record) => record.optimalStage.includes(value),
        sorter: (a, b) => a.optimalStage.length - b.optimalStage.length,
        sortOrder: sortedInfo.columnKey === 'optimalStage' && sortedInfo.order,
      }, {
        title: '适宜时间',
        dataIndex: 'optimalTime',
        key: 'optimalTime',
        filters: [
          { text: '早餐', value: '早餐' },
          { text: '早点', value: '早点' },
          { text: '午餐', value: '午餐' },
          { text: '点心', value: '点心' },
          { text: '晚餐', value: '晚餐' },
          { text: '夜宵', value: '夜宵' },
        ],
        filteredValue: filteredInfo.optimalTime || null,
        onFilter: (value, record) => record.optimalTime.includes(value),
        sorter: (a, b) => a.optimalTime.length - b.optimalTime.length,
        sortOrder: sortedInfo.columnKey === 'optimalTime' && sortedInfo.order,
      }, {
        title: '属性',
        dataIndex: 'property',
        key: 'property',
        filters: [
          { text: '低糖', value: '低糖' },
          { text: '低热量', value: '低热量' },
          { text: '无特殊属性', value: '无特殊属性' },
        ],
        filteredValue: filteredInfo.property || null,
        onFilter: (value, record) => record.property.includes(value),
        sorter: (a, b) => a.property.length - b.property.length,
        sortOrder: sortedInfo.columnKey === 'property' && sortedInfo.order,
      }, {
        title: '功效',
        dataIndex: 'efficacy',
        key: 'efficacy',
        filters: [
          { text: '补血', value: '补血' },
          { text: '活血', value: '活血' },
          { text: '清热', value: '清热' },
          { text: '祛湿', value: '祛湿' },
        ],
        filteredValue: filteredInfo.efficacy || null,
        onFilter: (value, record) => record.efficacy.includes(value),
        sorter: (a, b) => a.efficacy.length - b.efficacy.length,
        sortOrder: sortedInfo.columnKey === 'efficacy' && sortedInfo.order,
      }, {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <span>
            <Divider type="vertical" />
            <a href="javascript:;" onClick={this.openPanel.bind(this, record)} >
                <Icon type="edit" theme="filled" />
            </a>
            <Divider type="vertical" />
            <Popconfirm placement="topRight" title={confirmText} onConfirm={this.delete.bind(this, record)} okText="Yes" cancelText="No">
                <a href="javascript:;">
                    <Icon type="delete" theme="filled" />
                </a>
            </Popconfirm>
            <Divider type="vertical" />
          </span>
         ),
      }];
      return <div>
              <Button block icon="plus" width="40px" onClick={this.openPanel.bind(this, null)}>新增</Button>
              <Table columns={columns} dataSource={data} onChange={this.handleChange} />
              <RecipeDrawer onRef={this.onRef} />
            </div>;
    }
    
    onRef = (ref) => {
      this.drawer = ref
    }

    openPanel = (record) => {
      this.drawer.showDrawer(record);
    }
    
    delete = (record) => {
      message.info('Deleting [' + record.name + ']...');
    }

  }

  export default RecipeMain