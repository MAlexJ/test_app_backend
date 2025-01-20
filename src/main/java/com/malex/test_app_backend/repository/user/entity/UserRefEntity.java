package com.malex.test_app_backend.repository.user.entity;

/*
* @Schema()
export class UserRef {
	@Prop({ type: mongoose.Types.ObjectId })
	_id: mongoose.Types.ObjectId;
	@Prop()
	userId: number;
invitedBots: number[];
@Prop()
refLines: refLineInfo[];
@Prop()
parentRefId: number;
@Prop()
parentRefs: number[];
@Prop({ type: Map, of: Number, default: {} })
refSharedAmount: Map<number, number>;
@Prop({ type: Map, of: Number, default: {} })
refSharedCoefficient: Map<number, number>;
@Prop()
totalBonusSpeed: number;
@Prop()
invitedFriendsCount: number;

constructor(id: number, parentRefs: number[] | undefined) {
    this._id = toMongoId(id);
    this.userId = id;
    this.invitedfrens = [];
    this.parentRefId = parentRefs[0];
    this.parentRefs = parentRefs;
    this.invitedFriendsCount = 0;
    this.totalBonusSpeed = 0;
    this.refSharedAmount = new Map<number, number>();
    this.refSharedCoefficient = new Map<number, number>();
    this.refLines = [
    new refLineInfo(0, 0, 0),
            new refLineInfo(0, 0, 0),
            new refLineInfo(0, 0, 0),
		];
}
 */

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user_ref")
@TypeAlias("UserRef")
public class UserRefEntity {

    @MongoId private String id;

    @Indexed(unique = true)
    private Long userId;

}
