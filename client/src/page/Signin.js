import React,{useState} from 'react';
import {Grid, Button, TextField, Typography} from '@mui/material';
import {useHistory} from "react-router-dom";
import axios from "axios";

export const Signin = () => {
  const history = useHistory();
  const [info, setInfo] = useState({
    username:"",
    password:""
  })


  const onChangeInfo = (e) =>{
    setInfo({...info, [e.target.id]:e.target.value});
  }

  const postLogin = () =>{
    axios.post("http://localhost:8080/login",{
      username:info.username,
      password:info.password
    }).then(function (res){
      if(res.status === 200){
        // const myToken = res.headers.authorization.replace("Bearer ", "");
        const myToken = res.headers.authorization;
        localStorage.setItem("TOKEN", myToken);
        history.push("/api/user");
      } 
    }).catch(function (err){
      console.log(err);
    });
  }


  return (
    <Grid contianer={"true"} direction={"column"} alignItems={"center"} justifyContent={"center"} display={"flex"}
          style={{border: "1px solid #00000025", borderRadius: 5, padding: 20, width: 500}}>
      <h1> 로그인</h1>
      <Grid item style={{width:"80%"}}>
        <Grid >
          <TextField id={"username"} label={"username"} variant={"outlined"} style={{width: "100%"}} onChange={(e)=> onChangeInfo(e)}></TextField>
        </Grid>
        <Grid>
          <TextField type={"password"} id={"password"} label={"password"} variant={"outlined"} onChange={(e)=>onChangeInfo(e)}
                style={{width: "100%"}}></TextField>
        </Grid>

        <Grid display={"flex"} alignItems={"center"} justifyContent={"flex-end"} style={{width:"100%", padding:10}}>
          <Button variant="contained" onClick={postLogin}>로그인</Button>
          <Button color={"secondary"} variant="contained" onClick={() => {
            history.push("/signup")
          }}>회원가입</Button>
        </Grid>
      </Grid>
    </Grid>

  );

}