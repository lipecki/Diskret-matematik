
function  firstNodeList = getFirstNodeList (edge1, startingPoint)
  firstNodeList=zeros(1,3);
  for i = 1:rows(edge1)
    if(edge1(i,1) == startingPoint) 
      firstNodeList(startingPoint,:) = horzcat(startingPoint,startingPoint,0);
      firstNodeList(edge1(i,2),:) = horzcat(edge1(i,1:2),edge1(i,3));
    elseif(edge1(i,2) == startingPoint)
      firstNodeList(startingPoint,:) = horzcat(startingPoint,startingPoint,0);
      firstNodeList(edge1(i,1),:) = horzcat(edge1(i,2:-1:1),edge1(i,3));
    else 
      "what happened\n"
    endif;
    
  endfor;


endfunction
