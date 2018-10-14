import { BaseEntity, User } from './../../shared';

export class Absence implements BaseEntity {
    constructor(
        public id?: number,
        public dateAbsence?: any,
        public deleted?: boolean,
        public dateCreated?: any,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
        public collaborateur?: BaseEntity,
        public motif?: BaseEntity,
        public exercice?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
