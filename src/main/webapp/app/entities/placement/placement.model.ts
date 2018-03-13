import { BaseEntity } from './../../shared';

export class Placement implements BaseEntity {
    constructor(
        public id?: number,
        public nomClientFinal?: string,
        public nomSSII?: string,
        public consultants?: BaseEntity[],
        public bizDevs?: BaseEntity[],
    ) {
    }
}
