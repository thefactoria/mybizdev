import { BaseEntity } from './../../shared';

export class Equipe implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public bizDevs?: BaseEntity[],
    ) {
    }
}
