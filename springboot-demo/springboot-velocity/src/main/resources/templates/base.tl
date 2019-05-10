#set($errorMsg = '${wf:errorMessage(wf:lastErrorNode())}')
<?xml version='1.0' encoding='UTF-8'?>
<workflow-app name="antest002" xmlns="uri:oozie:workflow:0.5">
  <start to="ssh1"/>
  #parse("templates/ssh.tl")
  <kill name="fail">
    <message>Workflow failed, error message[${errorMsg}]</message>
  </kill>
  <end name="end"/>
</workflow-app>