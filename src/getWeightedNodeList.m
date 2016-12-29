
function  weightedNodeList = getWeightedNodeList (edges, startingPoint)
  weightedNodeList=zeros(1,3)
  e = edges(1,2:-1:1)
  while(rows(weightedNodeList) <= rows(edges))  
    for i = 1:rows(edges)
      if(edges(i,1) == startingPoint) 
        weightedNodeList(startingPoint,:) = horzcat(startingPoint,startingPoint,0)
        weightedNodeList(edges(i,2),:) = horzcat(edges(i,1:2),edges(i,3))
      elseif(edges(i,2) == startingPoint)
        weightedNodeList(startingPoint,:) = horzcat(startingPoint,startingPoint,0)
        weightedNodeList(edges(i,1),:) = horzcat(edges(i,2:-1:1),edges(i,3))
      else 
        "what happened\n"
      endif;
       
      #ska jag fortsätta eller bara returnera första värdet?
      
    endfor;
  endwhile;

endfunction
