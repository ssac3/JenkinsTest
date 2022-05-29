import React from 'react';
import {Grid, Button, TextField, Typography} from '@mui/material';
import {useHistory} from "react-router-dom";


export const Signin = () => {
  const history = useHistory();
  return (
    <Grid contianer={"true"} direction={"column"} alignItems={"center"} justifyContent={"center"} display={"flex"}
          style={{border: "1px solid #00000025", borderRadius: 5, padding: 20, width: 500}}>
      <h1> 로그인</h1>
      <Grid item style={{width:"80%"}}>
        <Grid >
          <TextField id={"id"} label={"id"} variant={"outlined"} style={{width: "100%"}}></TextField>
        </Grid>
        <Grid>
          <TextField type={"password"} id={"password"} label={"password"} variant={"outlined"}
                     style={{width: "100%"}}></TextField>
        </Grid>

        <Grid display={"flex"} alignItems={"center"} justifyContent={"flex-end"} style={{width:"100%", padding:10}}>
          <Button variant="contained">로그인</Button>
          <Button color={"secondary"} variant="contained" onClick={() => {
            history.push("/signup")
          }}>회원가입</Button>
        </Grid>
      </Grid>
    </Grid>

  );

}