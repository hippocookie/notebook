# Sharding

## Configuration

## Routing

## Execution

## Merge Result

SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?
