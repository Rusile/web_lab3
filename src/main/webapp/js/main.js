function resetInput() {
    document.querySelectorAll(".green").forEach(el => el.remove());
    document.querySelectorAll(".red").forEach(el => el.remove());
    $("#res_table > tbody").html("");
}

function computeClickCoords() {
    let target = $("#svg-graph");
    let r = $('[id="form:r_value"]').val();
    let x = Math.round(event.clientX - target.parent().position().left);
    let y = event.clientY - target.parent().position().top;
    let xValue = Math.round((x - 150) / (100 / r)) - r;
    let yValue = ((y - 150) / (-100 / r) + r * (4.500/2) +0.5).toFixed(3);
    $('[id="form:x_value"]').val(xValue);
    $('[id="form:y_value"]').val(yValue);
    $('[id="form:submit_button"]').click();
}