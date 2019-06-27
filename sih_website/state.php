<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <title>MDM Assam</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script defer src="https://use.fontawesome.com/releases/v5.0.9/js/all.js" integrity="sha384-8iPTk2s/jMVj81dnzb/iFR2sdA7u06vHJyyLlAd4snFpCl/SnyUjRrbdJsw1pGIl" crossorigin="anonymous"></script>
  <style media="screen">
    .topic{
      background-color: black;
      color: white;
    }
    .high{
      padding: 20px 20px 20px 20px;
      background-color: black;
      color: white;
      width: 25%;
    }
    .navbar-inverse{
      background-color: black;
    }
    @media (min-width: 768px)
    {
.navbar {
    border-radius: 0px!important;
}
}
  </style>
  </head>
  <body>

    <div class="container-fluid topic">
      <center><h1>MID DAY MEAL MONITORING GOVERNMENT OF ASSAM</h1></center>
    </div>

  <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">WebSiteName</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="#"><i class="fas fa-chart-line"></i>&nbsp;&nbsp;Dashboard</a></li>
        <li class="active"><a href="report.html"><i class="fas fa-calendar-alt"></i>&nbsp;&nbsp;Report</a></li>
        <li><a href="menu.html"><i class="fas fa-utensils"></i>&nbsp;&nbsp;Menu</a></li>
      </ul>
    </div>
  </div>
</nav>

      </div>
    </div>
    <br>
    <br>
    <h1>State Level Representation</h1>
    <br>
    <br>
    <?php
    echo "<table style='border: solid 1px black;border-collapse: collapse;' class='table'>";
    echo "<tr><th style='border-right: solid 1px black;'>School_name</th><th>School_Id</th><th>District</th></tr>";

    class TableRows extends RecursiveIteratorIterator {
        function __construct($it) {
            parent::__construct($it, self::LEAVES_ONLY);
        }

        function current() {
            return "<td style='width:150px;border:1px solid black;'>" . parent::current(). "</td>";
        }

        function beginChildren() {
            echo "<tr>";
        }

        function endChildren() {
            echo "</tr>" . "\n";
        }
    }

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "fundakhc_MDM";

    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $stmt = $conn->prepare("SELECT school_name,school_id,district FROM Supply_log");
        $stmt->execute();

        // set the resulting array to associative
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC);
        foreach(new TableRows(new RecursiveArrayIterator($stmt->fetchAll())) as $k=>$v) {
            echo $v;
        }
    }
    catch(PDOException $e) {
        echo "Error: " . $e->getMessage();
    }
    $conn = null;
    echo "</table>";
    ?>

    <footer class="container-fluid" style="background-color:black;width:100%;color:white;position:absolute;bottom:0px;">
      <center>
      <h3>Government of Assam</h3>
    </center>
    </footer>
  </body>
</html>
