  import {
    Table, Input, Button, Icon, Popconfirm, message
  } from 'antd';
  import Highlighter from 'react-highlight-words';
  import React, { Component } from 'react';
  import axios from 'axios';

  import RecipeDrawer from './RecipeDrawer'
  import constant from '../../core/constant'

  const ButtonGroup = Button.Group;
  const confirmText = '确定要删除吗?';
  const { RecipeProperties } = constant;

  class RecipeMain extends Component {
    componentDidMount() {
      this.refresh();
    }

    state = {
      searchText: '',
      filteredInfo: null,
      sortedInfo: null,
      selectedRow: null,
      data: null,
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
          textToHighlight={text == null ? "" : text.toString()}
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

    getFilter(value) {
      return { text: value, value: value };
    }

    getRecipePropertyColumn(recipeProperty) {
      let { sortedInfo, filteredInfo } = this.state;
      sortedInfo = sortedInfo || {};
      filteredInfo = filteredInfo || {};

      const key = recipeProperty.key;
      return {
        title: recipeProperty.label,
        dataIndex: key,
        key: key,
        filters: recipeProperty.sourceList.map(val => this.getFilter(val)),
        filteredValue: filteredInfo[key] || null,
        onFilter: (value, record) => record[key].includes(value),
        sorter: (a, b) => a[key].length - b[key].length,
        sortOrder: sortedInfo.columnKey === key && sortedInfo.order,
      }
    }

    getOperationColumn() {
      return {
          title: '操作',
          key: 'action',
          render: (text, record) => (
            <span>
              <ButtonGroup size="small">
                  <Button onClick={this.openPanel.bind(this, record)} >
                    <Icon type="edit" theme="filled" />
                  </Button>
                <Popconfirm placement="topRight" title={confirmText} onConfirm={this.delete.bind(this, record)} okText="Yes" cancelText="No">
                  <Button>
                    <Icon type="delete" theme="filled" />
                  </Button>
                </Popconfirm>
              </ButtonGroup>
            </span>
          )
      }
    }

    getRecipePropertyColumns() {
      const recipeProperties = RecipeProperties;
      const columns = [];
      recipeProperties.forEach(recipeProperty => {
        let column = this.getRecipePropertyColumn(recipeProperty);
        columns.push(column);
      });

      return columns;
    }

    getColumns() {
      const columns = [
        {
          title: '食谱名',
          dataIndex: 'name',
          key: 'name',
          ...this.getColumnSearchProps('name'),
        },
        {
          title: '食谱',
          dataIndex: 'content',
          key: 'content',
          ...this.getColumnSearchProps('content'),
        },
        ...this.getRecipePropertyColumns(),
        this.getOperationColumn()
      ];

      return columns;
    }

    render() {
      const columns = this.getColumns();
      return <div>
              <Button icon="plus" width="40px" onClick={this.openPanel.bind(this, null)}>新增</Button>
              <Table rowKey={record => record.id} columns={columns} dataSource={this.state.data} onChange={this.handleChange} />
              <RecipeDrawer data={this.state.record} visible={this.state.visible} id={this.state.id} refresh={this.refresh} close={this.close} />
            </div>;
    }

    openPanel = (record) => {
      this.setState({
        visible: true,
        id: record ? record.id : null
      });
    }

    delete = (record) => {
      message.info('Deleting [' + record.id + ']...');
      axios.post('http://localhost:8080/dish/delete', record)
      .then((res) => {
        this.refresh()
      }).catch((err) => {
        console.log(err)
      })
    }

    refresh = () => {
      axios.post('http://localhost:8080/dish/getall')
      .then((res) => {
        this.setState({data: res.data});
      })
      .catch((err) => {
        console.log(err)
      })
    }

    close = () => {
      this.setState({
        visible: false
      });
    }
  }

  export default RecipeMain