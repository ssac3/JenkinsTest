import React from "react";
import {Button} from "@mui/material";
import axios from "axios";
import {useHistory} from "react-router-dom";

export const Main = () => {
  const history = useHistory();
  const getMapping = () => {
    const config = {
      headers: {
        Authorization: localStorage.getItem("TOKEN")
      },
    }
    axios.get("http://localhost:8080/api/admin", config).then((res) => {
      res.status === 200 && history.push("/api/admin")
    }).catch((err) => {
      alert("관리자 권한이 없습니다.")
    })

  }

  return (
    <div>
      <h1>MAIN</h1>
      <Button color={"secondary"} variant="contained" onClick={getMapping}>관리자 페이지 이동</Button>
    </div>
  )


}
