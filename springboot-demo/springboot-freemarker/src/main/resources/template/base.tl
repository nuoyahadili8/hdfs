<?xml version='1.0' encoding='UTF-8'?>
<workflow-app name="antest002" xmlns="uri:oozie:workflow:0.5">
  <start to="ssh1"/>
  <#include "ssh.tl">
  <kill name="fail">
    <message>Workflow failed, error message[${errorMessage!"wf:errorMessage(wf:lastErrorNode())"}]</message>
  </kill>
  <end name="end"/>
</workflow-app>