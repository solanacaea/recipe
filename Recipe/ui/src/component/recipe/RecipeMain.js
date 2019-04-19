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
    category: 32,
    content: 'New York No. 1 Lake Park',
  }, {
    key: '2',
    name: 'Joe Black',
    category: 42,
    content: 'London No. 1 Lake Park',
  }, {
    key: '3',
    name: 'Jim Green',
    category: 32,
    content: 'Sidney No. 1 Lake Park',
  }, {
    key: '4',
    name: 'Jim Red',
    category: 32,
    content: 'London No. 2 Lake Park',
  }];
  
  class RecipeMain extends Component {
    state = {
      searchText: '',
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

    render() {
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
        ...this.getColumnSearchProps('category'),
      }, {
        title: '适宜阶段',
        dataIndex: 'category',
        key: 'category',
        ...this.getColumnSearchProps('category'),
      }, {
        title: '适宜时间',
        dataIndex: 'category',
        key: 'category',
        ...this.getColumnSearchProps('category'),
      }, {
        title: '属性',
        dataIndex: 'category',
        key: 'category',
        ...this.getColumnSearchProps('category'),
      }, {
        title: '功效',
        dataIndex: 'category',
        key: 'category',
        ...this.getColumnSearchProps('category'),
      }, {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <span>
            <Divider type="vertical" />
            <a href="javascript:;" onClick={this.openPanel}>
                <Icon type="edit" theme="filled" />
            </a>
            <Divider type="vertical" />
            <Popconfirm placement="topRight" title={confirmText} onConfirm={this.delete} okText="Yes" cancelText="No">
                <a href="javascript:;">
                    <Icon type="delete" theme="filled" />
                </a>
            </Popconfirm>
            <Divider type="vertical" />
          </span>
         ),
      }];
      return <div>
              <Button block icon="plus" width="40px" onClick={this.openPanel}>新增</Button>
              <Table columns={columns} dataSource={data} />
              <RecipeDrawer onRef={this.onRef} />
            </div>;
    }
    
    onRef = (ref) => {
      this.drawer = ref
    }

    openPanel = (e) => {
      this.drawer.showDrawer();
    }
    
    delete = (e) => {
      message.info('Clicked on Yes.');
    }

  }

  export default RecipeMain