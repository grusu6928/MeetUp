<#assign content>

<h1 lang="en" id ="header"> Choose Your Game </h1>



<div class = "neighbors_cor_form">
  <h2 lang="en">Neighbors Search (with coordinates)</h2>
  <form method="GET" action="/neighbors_cor">

      <input type="radio" id="naive" name="searchType" value="naive_neighbors">
      <label for="naive">Naive</label><br>

      <input type="radio" id="optimized" name="searchType" value="neighbors">
      <label for="optimized">Optimized</label><br>

      <p></p>

<#--      <label for="n_cor_version">Naive or Optimized? (input "naive" or "optimized", without quotes)</label><br>-->
<#--      <input type="text" id="n_cor_version" name="n_cor_version"><br>-->

      <label for="k">Number of neighbors to find:</label><br>
      <input type="text" id="k" name="k"><br>

      <label for="x">X Coordinate:</label><br>
      <input type="text" id="x" name="x"><br>
      <label for="y">Y Coordinate:</label><br>
      <input type="text" id="y" name="y"><br>
      <label for="z">Z Coordinate:</label><br>
      <input type="text" id="z" name="z"><br>

      <input type="submit">
  </form>
  ${n_cor_results}
</div>



<div class = "neighbors_name_form">
    <h2 lang="en">Neighbors Search (with star name)</h2>
    <form method="GET" action="/neighbors_name">

        <input type="radio" id="naive" name="searchType" value="naive_neighbors">
        <label for="naive">Naive</label><br>

        <input type="radio" id="optimized" name="searchType" value="neighbors">
        <label for="optimized">Optimized</label><br>

        <p></p>

        <#--      <label for="n_cor_version">Naive or Optimized? (input "naive" or "optimized", without quotes)</label><br>-->
        <#--      <input type="text" id="n_cor_version" name="n_cor_version"><br>-->

        <label for="k">Number of neighbors to find:</label><br>
        <input type="text" id="k" name="k"><br>

        <label for="starname">Star name:</label><br>
        <input type="text" id="starname" name="starname"><br>

        <input type="submit">
    </form>
    ${n_name_results}
</div>




<div class = "radius_cor_form">
    <h2 lang="en">Radius Search (with coordinates)</h2>
    <form method="GET" action="/radius_cor">

        <input type="radio" id="naive" name="searchType" value="naive_radius">
        <label for="naive">Naive</label><br>

        <input type="radio" id="optimized" name="searchType" value="radius">
        <label for="optimized">Optimized</label><br>

        <p></p>

        <#--      <label for="n_cor_version">Naive or Optimized? (input "naive" or "optimized", without quotes)</label><br>-->
        <#--      <input type="text" id="n_cor_version" name="n_cor_version"><br>-->

        <label for="r">Search radius:</label><br>
        <input type="text" id="r" name="r"><br>

        <label for="x">X Coordinate:</label><br>
        <input type="text" id="x" name="x"><br>
        <label for="y">Y Coordinate:</label><br>
        <input type="text" id="y" name="y"><br>
        <label for="z">Z Coordinate:</label><br>
        <input type="text" id="z" name="z"><br>

        <input type="submit">
    </form>
    ${r_cor_results}
</div>



<div class = "radius_name_form">
    <h2 lang="en">Radius Search (with star name)</h2>
    <form method="GET" action="/radius_name">

        <input type="radio" id="naive" name="searchType" value="naive_radius">
        <label for="naive">Naive</label><br>

        <input type="radio" id="optimized" name="searchType" value="radius">
        <label for="optimized">Optimized</label><br>

        <p></p>

        <#--      <label for="n_cor_version">Naive or Optimized? (input "naive" or "optimized", without quotes)</label><br>-->
        <#--      <input type="text" id="n_cor_version" name="n_cor_version"><br>-->

        <label for="r">Search radius:</label><br>
        <input type="text" id="r" name="r"><br>

        <label for="r_starname">Star name:</label><br>
        <input type="text" id="r_starname" name="r_starname"><br>

        <input type="submit">
    </form>
    ${r_name_results}
</div>





</#assign>
<#include "main.ftl">